import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { Observable, Subject, Subscription, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap, catchError, tap } from 'rxjs/operators';

import { AuthService } from '../../service/auth.service'; // Ajusta ruta
import { UsuarioBusqueda } from '../../model/usuarioBusqueda';

@Component({
  selector: 'app-buscador-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './buscador-usuarios.component.html',
  styleUrls: ['./buscador-usuarios.component.css']
})
export class BuscadorUsuariosComponent implements OnInit, OnDestroy {
  terminoBusqueda = '';
  private terminosBusquedaSubject = new Subject<string>();
  private searchSubscription!: Subscription; // Para desuscribirse

  resultadosBusqueda: UsuarioBusqueda[] = [];
  isLoading = false;
  errorBusqueda: string | null = null;
  mensajeInfo: string | null = null;

  esAdmin = false;
  loggedInUsername: string | null = null; // Para no permitir que el admin se modifique a sí mismo

  constructor(
    private authService: AuthService, // Cambiado de UsuarioService a AuthService
    private router: Router
  ) { }

  ngOnInit(): void {
    this.esAdmin = this.authService.getCurrentUserRole() === 'ADMIN';
    this.loggedInUsername = this.authService.getCurrentUsername();

    this.searchSubscription = this.terminosBusquedaSubject.pipe(
      debounceTime(400),
      distinctUntilChanged(),
      tap(() => {
        this.isLoading = true;
        this.resultadosBusqueda = [];
        this.errorBusqueda = null;
        this.mensajeInfo = null;
      }),
      switchMap(term => {
        if (!term.trim() || term.trim().length < 2) {
          this.isLoading = false;
          if (term.trim() !== '') {
            this.mensajeInfo = "Introduce al menos 2 caracteres para buscar.";
          }
          return of([]);
        }
        // Usar el método de AuthService
        return this.authService.searchUsernames(term.trim()).pipe(
          catchError(err => {
            console.error('Error en la búsqueda de usuarios:', err);
            this.errorBusqueda = 'Error al buscar usuarios. Inténtalo más tarde.';
            return of([]);
          })
        );
      })
    ).subscribe(usuarios => {
      this.isLoading = false;
      this.resultadosBusqueda = usuarios;
      if (!this.errorBusqueda && usuarios.length === 0 && this.terminoBusqueda.trim().length >= 2) {
        this.mensajeInfo = `No se encontraron usuarios con el nombre "${this.terminoBusqueda.trim()}".`;
      }
    });
  }

  ngOnDestroy(): void {
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  buscar(): void {
    if (this.terminoBusqueda.trim().length >= 2) {
      this.terminosBusquedaSubject.next(this.terminoBusqueda.trim());
    } else if (this.terminoBusqueda.trim() !== '') {
      this.resultadosBusqueda = [];
      this.mensajeInfo = "Introduce al menos 2 caracteres para buscar.";
    } else {
      this.resultadosBusqueda = [];
      this.mensajeInfo = null;
    }
  }

  onInputBusqueda(event: Event): void {
    const term = (event.target as HTMLInputElement).value;
    this.terminoBusqueda = term;
    this.terminosBusquedaSubject.next(term);
  }

  verPerfil(username: string): void {
    this.router.navigate(['/perfil', username]);
  }


  toggleAdmin(usuario: UsuarioBusqueda): void {
    if (!this.esAdmin || usuario.username === this.loggedInUsername) { // Admin no se modifica a sí mismo
      if (usuario.username === this.loggedInUsername) {
        alert("Un administrador no puede cambiar su propio rol de esta manera.");
      }
      return;
    }

    const nuevoEstadoAdmin = !usuario.isAdmin;
    const accion = nuevoEstadoAdmin ? 'hacer Administrador' : 'quitar como Administrador';

    if (confirm(`¿Seguro que quieres ${accion} a ${usuario.username}?`)) {
      const usuarioActualizado: UsuarioBusqueda = { ...usuario, isAdmin: nuevoEstadoAdmin };
      this.authService.updateUserStatus(usuarioActualizado).subscribe({
        next: (respuesta) => { // 'respuesta' es UsuarioBusqueda actualizado
          alert(`${usuario.username} ahora ${respuesta.isAdmin ? 'ES' : 'NO ES'} administrador.`);
          // Actualizar UI con la respuesta del servidor para asegurar consistencia
          const index = this.resultadosBusqueda.findIndex(u => u.username === respuesta.username);
          if (index > -1) {
            this.resultadosBusqueda[index] = respuesta;
          }
        },
        error: err => {
          console.error('Error al cambiar rol de admin:', err);
          alert(`Error al cambiar rol de admin para ${usuario.username}: ${err.error?.message || 'Error desconocido'}`);
        }
      });
    }
  }

  toggleActivo(usuario: UsuarioBusqueda): void {
    if (!this.esAdmin || usuario.username === this.loggedInUsername) { // Admin no desactiva su propia cuenta
      if (usuario.username === this.loggedInUsername) {
        alert("Un administrador no puede desactivar su propia cuenta desde aquí.");
      }
      return;
    }
    const nuevoEstadoActivo = !usuario.activo;
    const accion = nuevoEstadoActivo ? 'activar' : 'desactivar';

    if (confirm(`¿Seguro que quieres ${accion} al usuario ${usuario.username}?`)) {
      const usuarioActualizado: UsuarioBusqueda = { ...usuario, activo: nuevoEstadoActivo };
      this.authService.updateUserStatus(usuarioActualizado).subscribe({
        next: (respuesta) => { // 'respuesta' es UsuarioBusqueda actualizado
          alert(`Usuario ${usuario.username} ha sido ${respuesta.activo ? 'activado' : 'desactivado'}.`);
          const index = this.resultadosBusqueda.findIndex(u => u.username === respuesta.username);
          if (index > -1) {
            // Si el admin está viendo todos y desactiva uno, se mantiene pero cambia estado.
            // Si el admin NO ve inactivos (lógica de backend), el usuario desaparecería si se actualiza la lista.
            // Por ahora, solo actualizamos el estado en el objeto local.
            this.resultadosBusqueda[index] = respuesta;
            // Si el admin NO tiene permiso para ver inactivos y acaba de desactivar uno:
            if (!this.esAdminQueVeInactivos() && !respuesta.activo) { // Necesitaríamos un flag o lógica para esAdminQueVeInactivos
              this.resultadosBusqueda.splice(index, 1);
            }
          }
        },
        error: err => {
          console.error('Error al cambiar estado activo:', err);
          alert(`Error al ${accion} a ${usuario.username}: ${err.error?.message || 'Error desconocido'}`);
        }
      });
    }
  }

  private esAdminQueVeInactivos(): boolean {
    return this.esAdmin; // Asumimos que si es admin, la búsqueda ya le trae todo.
  }
}