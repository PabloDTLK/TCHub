import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute, ParamMap } from '@angular/router'; // ParamMap
import { forkJoin, map, switchMap, of, Observable, Subscription, catchError, filter } from 'rxjs';
import { Coleccion, VarianteBaseEnColeccionDTO } from '../../model/coleccion';
import { Variante } from '../../model/variante';
import { VarianteEnColeccion } from '../../model/varianteEnColeccion';
import { AuthService } from '../../service/auth.service';
import { CartaBackendService } from '../../service/carta-backend.service';
import { ColeccionService } from '../../service/coleccion.service';

interface ColeccionConDetallesParaUI {
  // Hereda/copia propiedades de Coleccion que no sean la lista de variantes base
  id?: string;
  nombre: string;
  descripcion: string;
  // usuarioId?: string; // Si lo necesitas en el template

  // Propiedad específica para las variantes enriquecidas que usará el template
  variantes: VarianteEnColeccion[];
  valorTotalCalculado?: number;
  estaDesplegada?: boolean;
}

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit, OnDestroy {
  // Esta es la propiedad que el HTML usará para el *ngFor de colecciones
  coleccionesConValor: ColeccionConDetallesParaUI[] = [];

  perfilUsername: string | null = null;
  usuarioLogueadoUsername: string | null = null;
  usuarioLogueadoRol: string | null = null;
  esPerfilPropio = false;

  isLoading = false;
  error: string | null = null;
  pageTitle = 'Perfil de Usuario';

  private routeSub!: Subscription;
  private dataSub!: Subscription;

  constructor(
    private coleccionService: ColeccionService,
    private authService: AuthService,
    private cartaBackendService: CartaBackendService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.isLoading = true; // Iniciar carga
    this.usuarioLogueadoUsername = this.authService.getCurrentUsername();
    this.usuarioLogueadoRol = this.authService.getCurrentUserRole();

    this.dataSub = this.route.data.subscribe(data => {
      if (data['esMiPerfil']) {
        this.esPerfilPropio = true;
        this.perfilUsername = this.usuarioLogueadoUsername;
        this.pageTitle = 'Mi Perfil';
        if (this.perfilUsername) {
          this.cargarDatosDelPerfil();
        } else {
          console.warn("PerfilComponent: Ruta /perfil accedida sin usuario logueado (authGuard debería haber prevenido esto). Redirigiendo a login.");
          this.router.navigate(['/login']);
        }
      }
    });

    this.routeSub = this.route.paramMap.subscribe(params => {
      if (!this.esPerfilPropio) { // Solo actuar si no fue manejado por 'data' (ruta /perfil)
        const usernameFromRoute = params.get('username');
        if (usernameFromRoute) {
          this.perfilUsername = usernameFromRoute;
          this.pageTitle = `Perfil de ${this.perfilUsername}`;
          this.esPerfilPropio = this.usuarioLogueadoUsername === this.perfilUsername;
          if (this.esPerfilPropio) {
            this.pageTitle = 'Mi Perfil';
          }
          this.cargarDatosDelPerfil();
        } else if (!this.perfilUsername) { // Si no hay :username y tampoco se seteó por 'data'
          this.error = "Perfil no especificado en la URL.";
          this.isLoading = false;
          console.warn("PerfilComponent: No se encontró 'username' en params y no es 'esMiPerfil'.");
          this.router.navigate(['/catalogo']);
        }
      }
    });
  }

  ngOnDestroy(): void {
    if (this.routeSub) this.routeSub.unsubscribe();
    if (this.dataSub) this.dataSub.unsubscribe();
  }

  cargarDatosDelPerfil(): void {
    if (!this.perfilUsername) {
      this.error = "Nombre de usuario del perfil no disponible para la carga.";
      this.isLoading = false;
      return;
    }
    this.isLoading = true; // Asegurar que isLoading esté true al (re)iniciar la carga
    this.error = null;
    this.coleccionesConValor = []; // Resetear antes de cargar
    this.cargarColeccionesConValores(this.perfilUsername);
  }

  cargarColeccionesConValores(usernameDelPerfil: string): void {
    console.log(`PerfilComponent: Iniciando carga de colecciones para ${usernameDelPerfil}`);
    let coleccionesObservable: Observable<Coleccion[]>; // Espera el tipo Coleccion de coleccion.ts

    if (usernameDelPerfil === this.usuarioLogueadoUsername && this.coleccionService.getColeccionesUsuario) {
      coleccionesObservable = this.coleccionService.getColeccionesUsuario();
    } else if (this.coleccionService.getColeccionesPorUsername) {
      coleccionesObservable = this.coleccionService.getColeccionesPorUsername(usernameDelPerfil);
    } else {
      this.error = "Servicio de colección no configurado adecuadamente para obtener colecciones.";
      this.isLoading = false;
      return;
    }

    coleccionesObservable.pipe(
      switchMap((coleccionesDesdeServicio: Coleccion[]) => {
        if (!coleccionesDesdeServicio || coleccionesDesdeServicio.length === 0) {
          console.log(`PerfilComponent: No se encontraron colecciones para ${usernameDelPerfil} desde el backend.`);
          // this.isLoading = false; //isLoading se manejará en el subscribe final
          return of([]);
        }
        console.log(`PerfilComponent: Recibidas ${coleccionesDesdeServicio.length} colecciones crudas:`, JSON.parse(JSON.stringify(coleccionesDesdeServicio)));

        const observablesParaCadaColeccion = coleccionesDesdeServicio.map(coleccionBase => {
          // 'coleccionBase' es de tipo Coleccion y tiene 'listaVariantes'
          const variantesBaseParaProcesar: VarianteBaseEnColeccionDTO[] = coleccionBase.listaVariantes || [];

          // Objeto que construiremos y será de tipo ColeccionConDetallesParaUI
          const coleccionParaUI: Partial<ColeccionConDetallesParaUI> = {
            id: coleccionBase.id,
            nombre: coleccionBase.nombre,
            descripcion: coleccionBase.descripcion,
            variantes: [], // Se llenará con VarianteEnColeccion enriquecidas
            valorTotalCalculado: 0,
            estaDesplegada: false
          };

          if (variantesBaseParaProcesar.length === 0) {
            return of(coleccionParaUI as ColeccionConDetallesParaUI);
          }

          const detalleVariantesObservables = variantesBaseParaProcesar.map(varianteConCantidad => {
            return this.cartaBackendService.getVarianteDetalleById(varianteConCantidad.varianteUniversalId).pipe(
              map((detalleCompletoVariante: Variante) => {
                const varianteUIPoblada = new VarianteEnColeccion(
                  varianteConCantidad.varianteUniversalId,
                  varianteConCantidad.cantidad,
                  detalleCompletoVariante.nombre,
                  detalleCompletoVariante.imagen,
                  detalleCompletoVariante.idiomaCodigo
                );
                return {
                  varianteParaUI: varianteUIPoblada,
                  precioUnitario: detalleCompletoVariante.precioMedio || 0,
                  cantidadEnColeccion: varianteConCantidad.cantidad
                };
              }),
              catchError(err => {
                console.warn(`PerfilComponent: Error al cargar detalle para variante ${varianteConCantidad.varianteUniversalId} en colección '${coleccionBase.nombre}':`, err);
                const varianteErrorUI = new VarianteEnColeccion(
                  varianteConCantidad.varianteUniversalId,
                  varianteConCantidad.cantidad,
                  `Error (${varianteConCantidad.varianteUniversalId.substring(0,5)}...)`,
                  'assets/images/placeholder-carta-error.png',
                  'ERR'
                );
                return of({
                  varianteParaUI: varianteErrorUI,
                  precioUnitario: 0,
                  cantidadEnColeccion: varianteConCantidad.cantidad
                });
              })
            );
          });

          return forkJoin(detalleVariantesObservables).pipe(
            map(variantesProcesadas => {
              let valorTotalColeccion = 0;
              const variantesCompletasParaUI: VarianteEnColeccion[] = [];
              for (const procesada of variantesProcesadas) {
                valorTotalColeccion += (procesada.precioUnitario * procesada.cantidadEnColeccion);
                variantesCompletasParaUI.push(procesada.varianteParaUI);
              }
              // Asignamos las variantes enriquecidas al campo 'variantes' de nuestro objeto UI
              (coleccionParaUI as ColeccionConDetallesParaUI).variantes = variantesCompletasParaUI;
              (coleccionParaUI as ColeccionConDetallesParaUI).valorTotalCalculado = valorTotalColeccion;
              return coleccionParaUI as ColeccionConDetallesParaUI;
            })
          );
        });
        return forkJoin(observablesParaCadaColeccion); // Devuelve Observable<ColeccionConDetallesParaUI[]>
      }),
      catchError(err => {
        console.error(`PerfilComponent: Error general al procesar colecciones para ${usernameDelPerfil}:`, err);
        this.error = `No se pudieron cargar completamente las colecciones para ${usernameDelPerfil}.`;
        // this.isLoading = false; // isLoading se maneja en el subscribe final
        return of([]);
      })
    ).subscribe({
      next: (coleccionesFinales: ColeccionConDetallesParaUI[]) => {
        this.coleccionesConValor = coleccionesFinales; // Correcto: asignar a la propiedad que usa el template
        this.isLoading = false; // Carga finalizada (con o sin datos, con o sin error previo manejado)
        if (this.coleccionesConValor.length > 0) { // Usar la propiedad correcta
            console.log('PerfilComponent: Carga completa. Colecciones finales para la vista:', JSON.parse(JSON.stringify(this.coleccionesConValor)));
        } else if (!this.error) {
            console.log(`PerfilComponent: Carga completa. No hay colecciones para mostrar para ${usernameDelPerfil}.`);
        }
      },
      error: (err) => { // Error en el observable principal (poco probable si los catchError internos devuelven of([]))
        this.error = 'Ocurrió un error inesperado al finalizar la carga de colecciones.';
        this.isLoading = false;
        console.error('PerfilComponent: Error en la suscripción final de carga de colecciones:', err);
      }
  });
  }

  toggleColeccion(coleccion: ColeccionConDetallesParaUI): void {
    coleccion.estaDesplegada = !coleccion.estaDesplegada;
  }

  get mostrarBotonEditar(): boolean {
    const usernameLogueado = this.authService.getCurrentUsername();
    if (!usernameLogueado) return false;
    if (usernameLogueado === this.perfilUsername) return true;
    if (this.usuarioLogueadoRol === 'ADMIN') return true;
    return false;
  }

  irAEditarColecciones(): void {
    const usernameLogueado = this.authService.getCurrentUsername();

    if (this.esPerfilPropio || (this.usuarioLogueadoRol === 'ADMIN' && this.perfilUsername)) {
      // Si es mi perfil, o soy admin viendo el perfil de otro, navego a la gestión de colecciones de ESE perfil.
      // Si this.perfilUsername es el mismo que el logueado, irá a /mis-colecciones
      // Si this.perfilUsername es de otro, irá a /mis-colecciones/otroUsername
      this.router.navigate(['/mis-colecciones', this.perfilUsername]);
    } else {
      // Caso de seguridad, no debería ocurrir si el botón se muestra correctamente
      console.warn("Intento no autorizado de ir a editar colecciones.");
    }
  }
}