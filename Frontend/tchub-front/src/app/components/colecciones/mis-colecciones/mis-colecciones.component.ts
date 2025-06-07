import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router, RouterModule } from '@angular/router';
import { ColeccionService } from '../../../service/coleccion.service';
import { Coleccion } from '../../../model/coleccion';
import { CommonModule } from '@angular/common';
import { Observable, Subscription } from 'rxjs';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'app-mis-colecciones',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './mis-colecciones.component.html',
  styleUrls: ['./mis-colecciones.component.css']
})
export class MisColeccionesComponent implements OnInit {
  colecciones: Coleccion[] = [];
  isLoading = false;
  error: string | null = null;

  pageTitle = 'Mis Colecciones';
  usernameDelContexto: string | null = null; // Username cuyas colecciones se están mostrando
  esVistaPropia = true; // ¿Está el usuario viendo sus propias colecciones?
  usuarioLogueadoRol: string | null = null;

  private routeSub!: Subscription;

  constructor(
    private coleccionService: ColeccionService,
    private authService: AuthService, // Para rol y username del logueado
    private router: Router,
    private route: ActivatedRoute // Para leer el parámetro :username
  ) {}

  ngOnInit(): void {
    this.usuarioLogueadoRol = this.authService.getCurrentUserRole();
    const usernameLogueado = this.authService.getCurrentUsername();

    this.routeSub = this.route.paramMap.subscribe((params: ParamMap) => {
      const usernameFromRoute = params.get('username');

      if (usernameFromRoute) {
        // Estamos viendo/editando las colecciones de un usuario específico
        // Solo permitido para admins (el guardián de ruta ya verificó el login,
        // aquí podríamos añadir una verificación de rol si es necesario, aunque
        // la lógica de mostrar el botón de edición en PerfilComponent ya lo hace).
        if (this.usuarioLogueadoRol === 'ADMIN') {
          this.usernameDelContexto = usernameFromRoute;
          this.pageTitle = `Colecciones de ${this.usernameDelContexto}`;
          this.esVistaPropia = usernameLogueado === this.usernameDelContexto;
        } else {
          // Si un no-admin intenta acceder a /mis-colecciones/:otroUsuario, redirigir
          console.warn(`Acceso no autorizado a colecciones de ${usernameFromRoute} por ${usernameLogueado}`);
          this.router.navigate(['/catalogo']); // O a una página de no autorizado
          return;
        }
      } else {
        // No hay username en la ruta, cargamos las del usuario logueado
        this.usernameDelContexto = usernameLogueado;
        this.pageTitle = 'Mis Colecciones';
        this.esVistaPropia = true;
      }

      if (this.usernameDelContexto) {
        this.cargarColecciones();
      } else if (!usernameFromRoute && !usernameLogueado) {
        // Caso borde: ruta /mis-colecciones pero el usuario no está logueado (authGuard debería haberlo prevenido)
        this.router.navigate(['/login']);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.routeSub) {
      this.routeSub.unsubscribe();
    }
  }

  cargarColecciones(): void {
    if (!this.usernameDelContexto) {
      this.error = "No se puede determinar el usuario para cargar colecciones.";
      return;
    }

    this.isLoading = true;
    this.error = null;
    let coleccionesObservable: Observable<Coleccion[]>;

    if (this.esVistaPropia && this.coleccionService.getColeccionesUsuario) {
      coleccionesObservable = this.coleccionService.getColeccionesUsuario();
    } else if (this.coleccionService.getColeccionesPorUsername) { // Para admin viendo colecciones de otro
      coleccionesObservable = this.coleccionService.getColeccionesPorUsername(this.usernameDelContexto);
    } else {
      this.error = "Servicio no configurado para obtener colecciones de este usuario.";
      this.isLoading = false;
      return;
    }

    coleccionesObservable.subscribe({
      next: (data) => {
        this.colecciones = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error al cargar colecciones:', err);
        this.error = 'No se pudieron cargar las colecciones. Inténtalo de nuevo más tarde.';
        this.isLoading = false;
      }
    });
  }

  // El botón "Crear Nueva Colección" solo debe crear para el usuario logueado (si esVistaPropia)
  // o para el usuario cuyas colecciones está gestionando el admin.
  // El backend decidirá a quién asignar la nueva colección basado en el token (si es propia)
  // o en un posible parámetro 'usuarioId'/'username' si un admin crea para otro.
  // Por simplicidad, el editor siempre crea para el usuario inferido por el backend o
  // si un admin está en /coleccion/nueva, el backend debe tener una forma de saber para quién es.
  // Por ahora, el botón "Crear" siempre llevará al editor para el usuario actual o el gestionado.
  crearNuevaColeccion(): void {
    if (this.esVistaPropia || this.usuarioLogueadoRol === 'ADMIN') {
      // Si un admin está gestionando las colecciones de 'usernameDelContexto',
      // el EditorColeccionComponent debería saber para quién está creando la colección.
      // Esto podría pasarse como queryParam o el backend manejarlo si el admin tiene permisos.
      // Por ahora, la ruta /coleccion/nueva es genérica.
      this.router.navigate(['/coleccion/nueva'], { queryParams: { usuario: this.usernameDelContexto !== this.authService.getCurrentUsername() ? this.usernameDelContexto : undefined } });
    }
  }

  editarColeccion(id: string | undefined): void {
    if (id) {
      // Si un admin edita, también se pasa el username del contexto para que el editor sepa de quién es
      this.router.navigate(['/coleccion/editar', id], { queryParams: { usuario: this.usernameDelContexto !== this.authService.getCurrentUsername() ? this.usernameDelContexto : undefined } });
    } else {
      console.error('No se puede editar una colección sin ID.');
    }
  }

  eliminarColeccion(id: string | undefined, nombreColeccion: string): void {
    if (!id) return;
    // La lógica de eliminación debe considerar permisos si un admin elimina la de otro.
    // El servicio de backend y el endpoint deben manejar esto.
    if (confirm(`¿Estás seguro de que quieres eliminar la colección "${nombreColeccion}"?`)) {
      this.isLoading = true;
      this.coleccionService.deleteColeccion(id).subscribe({ // Asume que deleteColeccion usa el token para permisos
        next: () => {
          this.colecciones = this.colecciones.filter(c => c.id !== id);
          this.isLoading = false;
        },
        error: (err) => {
          this.error = `Error al eliminar la colección "${nombreColeccion}".`;
          this.isLoading = false;
        }
      });
    }
  }
}
