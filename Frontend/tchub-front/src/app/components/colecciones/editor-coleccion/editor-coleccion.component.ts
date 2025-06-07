// src/app/components/colecciones/editor-coleccion/editor-coleccion.component.ts
import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule, ParamMap } from '@angular/router';
import { Subscription, switchMap, of, forkJoin, map, catchError, tap, firstValueFrom, take } from 'rxjs'; // Importa take y firstValueFrom

// Modelos
import { Coleccion, VarianteBaseEnColeccionDTO } from '../../../model/coleccion';
import { VarianteEnColeccion } from '../../../model/varianteEnColeccion';
import { Variante } from '../../../model/variante';

// Servicios
import { ColeccionService } from '../../../service/coleccion.service';
import { CartaBackendService } from '../../../service/carta-backend.service';
import { AuthService } from '../../../service/auth.service'; // Necesitamos AuthService

// Componentes hijos
import { CatalogoComponent } from '../../catalogo/catalogo.component';
import { SeleccionarVarianteModalComponent } from '../seleccionar-variante-modal/seleccionar-variante-modal.component';

// DTO para el backend
import { ColeccionConVariantesDTO, ColeccionVariantesListDTO } from '../../../model/dto.model';


interface VarianteTemporalParaModal {
  multiversalId: string; // ID conceptual de la carta (ej: edicion.multiversal_id)
  nombreBaseCarta: string;
}

@Component({
  selector: 'app-editor-coleccion',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    CatalogoComponent,
    SeleccionarVarianteModalComponent
  ],
  templateUrl: './editor-coleccion.component.html',
  styleUrls: ['./editor-coleccion.component.css']
})
export class EditorColeccionComponent implements OnInit, OnDestroy {
  coleccion: Coleccion = new Coleccion('', '', []);
  cartasSeleccionadas: VarianteEnColeccion[] = []; // Para la UI y construir el DTO

  isEditMode = false;
  coleccionId: string | null = null; // ID de la colección que se está editando
  pageTitle = 'Nueva Colección';
  isLoading = false;
  isSaving = false;
  error: string | null = null;
  nombreOriginal: string | null = null;

  // Para manejo de admin editando/creando para otro usuario
  usernameContextoColeccion: string | null = null; // Username para el que se está editando/creando la colección
  esAdminGestionandoParaOtro = false;

  private routeParamsSub!: Subscription;
  // queryParamsSubscription no es necesaria si usamos firstValueFrom
  private guardarSub!: Subscription;

  showVarianteModal = false;
  varianteParaModal: VarianteTemporalParaModal | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private coleccionService: ColeccionService,
    private cartaBackendService: CartaBackendService,
    private authService: AuthService,
    private location: Location
  ) {}

  async ngOnInit(): Promise<void> {
    this.isLoading = true;
    const usuarioLogueadoUsername = this.authService.getCurrentUsername();
    const usuarioLogueadoRol = this.authService.getCurrentUserRole();

    // 1. Leer Query Params para 'usuario' (caso admin gestionando)
    // Usamos firstValueFrom para obtener el valor una vez y simplificar
    const queryParams = await firstValueFrom(this.route.queryParamMap.pipe(take(1)));
    const usuarioQueryParam = queryParams.get('usuario');

    if (usuarioQueryParam && usuarioLogueadoRol === 'ADMIN') {
      this.usernameContextoColeccion = usuarioQueryParam;
      this.esAdminGestionandoParaOtro = this.usernameContextoColeccion !== usuarioLogueadoUsername; // Podría ser admin editando las suyas propias
      console.log(`Editor: Admin detectado. Contexto de usuario para la colección: ${this.usernameContextoColeccion}`);
    } else {
      this.usernameContextoColeccion = usuarioLogueadoUsername; // Por defecto, es para el usuario logueado
      this.esAdminGestionandoParaOtro = false;
      console.log(`Editor: Contexto de usuario para la colección (propia): ${this.usernameContextoColeccion}`);
    }

    // 2. Leer Route Params para 'id' (caso edición)
    this.routeParamsSub = this.route.paramMap.pipe(
      switchMap(params => {
        this.coleccionId = params.get('id');
        if (this.coleccionId) {
          // Modo Edición
          this.isEditMode = true;
          this.pageTitle = this.esAdminGestionandoParaOtro && this.usernameContextoColeccion
            ? `Editar Colección de ${this.usernameContextoColeccion}`
            : 'Editar Mi Colección';
          console.log(`Editor Modo Edición - Cargando colección ID: ${this.coleccionId}`);
          // El backend debe validar si el usuario (admin o propietario) puede acceder a esta colección
          return this.coleccionService.getColeccionById(this.coleccionId);
        } else {
          // Modo Creación
          this.isEditMode = false;
          this.pageTitle = this.esAdminGestionandoParaOtro && this.usernameContextoColeccion
            ? `Nueva Colección para ${this.usernameContextoColeccion}`
            : 'Nueva Colección';
          this.coleccion = new Coleccion('', '', []);
          this.cartasSeleccionadas = [];
          this.isLoading = false; // No hay nada más que cargar para una nueva
          return of(null);
        }
      }),
      switchMap((coleccionCargada: Coleccion | null) => {
        if (!coleccionCargada) {
          if (!this.isEditMode) this.isLoading = false;
          return of([]); // Devuelve array vacío de VarianteEnColeccion
        }

        this.coleccion.id = coleccionCargada.id;
        this.coleccion.nombre = coleccionCargada.nombre;
        this.coleccion.descripcion = coleccionCargada.descripcion;
        this.nombreOriginal = coleccionCargada.nombre;
        // Aquí this.coleccion.listaVariantes es undefined porque el constructor lo hace
        // pero coleccionCargada.listaVariantes SÍ tiene los datos base.

        const variantesBaseDelBackend: VarianteBaseEnColeccionDTO[] = coleccionCargada.listaVariantes || [];
        console.log('Editor: Variantes base recibidas del backend:', variantesBaseDelBackend);

        if (variantesBaseDelBackend.length > 0) {
          const observablesDetalle = variantesBaseDelBackend.map(vec => {
            return this.cartaBackendService.getVarianteDetalleById(vec.varianteUniversalId).pipe(
              map((detalleCompletoVariante: Variante) => {
                return new VarianteEnColeccion(
                  vec.varianteUniversalId,
                  vec.cantidad,
                  detalleCompletoVariante.nombre,
                  detalleCompletoVariante.imagen,
                  detalleCompletoVariante.idiomaCodigo
                );
              }),
              catchError(err => {
                console.warn(`Editor: No se pudo cargar detalle para variante ${vec.varianteUniversalId}:`, err);
                return of(new VarianteEnColeccion(
                    vec.varianteUniversalId, vec.cantidad,
                    'Error al cargar', 'assets/images/placeholder-carta-error.png', 'ERR'
                ));
              })
            );
          });
          return forkJoin(observablesDetalle);
        }
        return of([]);
      }),
      catchError(err => {
        console.error('Editor: Error al cargar datos de la colección para editar:', err);
        this.error = `No se pudo cargar la colección. ${this.isEditMode ? 'Volviendo...' : ''}`;
        this.isLoading = false;
        if (this.isEditMode) {
          // Podríamos redirigir a /mis-colecciones o al perfil del usuario si es admin gestionando
          const targetRoute = this.esAdminGestionandoParaOtro && this.usernameContextoColeccion
            ? ['/perfil', this.usernameContextoColeccion]
            : ['/mis-colecciones'];
          setTimeout(() => this.router.navigate(targetRoute), 3000);
        }
        return of([]);
      })
    ).subscribe((variantesEnriquecidasParaUI: VarianteEnColeccion[]) => {
      this.cartasSeleccionadas = [...variantesEnriquecidasParaUI];
      // Actualizamos 'variantesUI' en el objeto 'this.coleccion' para mantener la consistencia del modelo
      // aunque el template principal del resumen itera sobre 'cartasSeleccionadas'.
      this.coleccion.variantesUI = [...variantesEnriquecidasParaUI];

      // Solo poner isLoading a false aquí si estábamos en modo edición
      // o si es modo creación y no hubo un coleccionId (ya se manejó antes).
      if(this.isEditMode) {
          this.isLoading = false;
      }
      console.log('EditorComponent: ngOnInit completado. Cartas seleccionadas (UI):', this.cartasSeleccionadas);
    });
  }

  ngOnDestroy(): void {
    if (this.routeParamsSub) this.routeParamsSub.unsubscribe();
    if (this.guardarSub) this.guardarSub.unsubscribe();
  }

  manejarCartaClickeadaDelCatalogo(multiversalId: string): void {
    console.log("Editor: Clic en carta del catálogo, multiversalId:", multiversalId);
    // Lógica para obtener un nombre base de la carta (puede ser un placeholder o de un servicio)
    const nombreBase = `Carta Conceptual ${multiversalId.substring(0,8)}...`; // Placeholder
    this.varianteParaModal = { multiversalId: multiversalId, nombreBaseCarta: nombreBase };
    this.showVarianteModal = true;
  }

  handleModalCerradoConSeleccion(seleccion?: VarianteEnColeccion): void {
    this.showVarianteModal = false;
    this.varianteParaModal = null;
    if (seleccion) {
      if (seleccion.cantidad > 0) {
        this.agregarOActualizarCartaEnColeccion(seleccion);
      } else {
        this.eliminarCartaDeSeleccion(seleccion.varianteUniversalId);
      }
    }
  }

  agregarOActualizarCartaEnColeccion(varianteSeleccionada: VarianteEnColeccion): void {
    const existenteIndex = this.cartasSeleccionadas.findIndex(
      (v) => v.varianteUniversalId === varianteSeleccionada.varianteUniversalId
    );
    if (existenteIndex > -1) {
      this.cartasSeleccionadas[existenteIndex] = varianteSeleccionada; // Reemplazar para actualizar todos los campos
    } else {
      this.cartasSeleccionadas.push(varianteSeleccionada);
    }
  }

  eliminarCartaDeSeleccion(varianteUniversalIdAEliminar: string): void {
    this.cartasSeleccionadas = this.cartasSeleccionadas.filter(
      (v) => v.varianteUniversalId !== varianteUniversalIdAEliminar
    );
  }

  actualizarCantidad(varianteUniversalId: string, event: Event | number): void {
    const nuevaCantidad = typeof event === 'number' ? event : parseInt((event.target as HTMLInputElement)?.value, 10);
    if (isNaN(nuevaCantidad)) return;

    const index = this.cartasSeleccionadas.findIndex(v => v.varianteUniversalId === varianteUniversalId);
    if (index > -1) {
      if (nuevaCantidad > 0) {
        this.cartasSeleccionadas[index].cantidad = nuevaCantidad;
      } else {
        this.cartasSeleccionadas.splice(index, 1);
      }
    }
  }

  onSubmitForm(form: NgForm): void {
    this.error = null;
    if (form.invalid) {
      this.error = 'Por favor, corrige los errores en el formulario.';
      Object.keys(form.controls).forEach(field => {
        form.controls[field].markAsTouched({ onlySelf: true });
      });
      return;
    }

    this.isSaving = true;
    const listaVariantesDTO: ColeccionVariantesListDTO[] = this.cartasSeleccionadas.map(vec => ({
      varianteUniversalId: vec.varianteUniversalId,
      cantidad: vec.cantidad
    }));

    const coleccionPayload: ColeccionConVariantesDTO = {
      id: this.isEditMode ? this.coleccionId! : undefined,
      nombre: this.coleccion.nombre,
      descripcion: this.coleccion.descripcion,
      listaVariantes: listaVariantesDTO,
      // Si es un admin creando/editando para otro, el backend necesita saberlo.
      // Esto asume que el backend puede tomar 'usernamePropietario'.
      // Si no, el backend debería inferir del token para el usuario logueado
      // y para un admin, podría necesitar que el 'id' de la colección ya tenga el dueño correcto
      // o que haya un endpoint específico para operaciones de admin.
      ...(this.esAdminGestionandoParaOtro && this.usernameContextoColeccion && { usernamePropietario: this.usernameContextoColeccion })
    };

    console.log('Editor: Enviando DTO al backend:', coleccionPayload);

    this.guardarSub = this.coleccionService.saveColeccionConFormatoDTO(coleccionPayload).subscribe({
      next: () => {
        this.isSaving = false;
        if (this.esAdminGestionandoParaOtro && this.usernameContextoColeccion) {
          this.router.navigate(['/perfil', this.usernameContextoColeccion]);
        } else {
          this.router.navigate(['/mis-colecciones']);
        }
      },
      error: (err) => {
        console.error('Error al guardar la colección:', err);
        this.error = err.error?.message || err.error?.error || err.message || 'Ocurrió un error al guardar la colección.';
        this.isSaving = false;
      }
    });
  }

  cancelar(): void {
    if (this.esAdminGestionandoParaOtro && this.usernameContextoColeccion) {
      this.router.navigate(['/perfil', this.usernameContextoColeccion]);
    } else if (this.isEditMode) {
      this.router.navigate(['/mis-colecciones']);
    } else {
      this.location.back(); // O a /mis-colecciones si es más apropiado
    }
  }
}