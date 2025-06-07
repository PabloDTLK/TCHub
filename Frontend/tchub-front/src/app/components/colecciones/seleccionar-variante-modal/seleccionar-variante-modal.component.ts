import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EdicionDetalle } from '../../../model/edicionDetalle';
import { Variante } from '../../../model/variante';
import { VarianteEnColeccion } from '../../../model/varianteEnColeccion';
import { CartaBackendService } from '../../../service/carta-backend.service';

@Component({
  selector: 'app-seleccionar-variante-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './seleccionar-variante-modal.component.html',
  styleUrls: ['./seleccionar-variante-modal.component.css']
})
export class SeleccionarVarianteModalComponent implements OnInit {
  @Input() multiversalId!: string; // ID conceptual de la carta (ej: edicion.multiversal_id)
  @Input() nombreBaseCarta!: string; // Nombre general de la carta para mostrar en el título del modal

  @Output() cerrarModal = new EventEmitter<VarianteEnColeccion | undefined>(); // Emite la selección o undefined si se cancela

  edicionDetalle: EdicionDetalle | null = null;
  variantesDisponibles: Variante[] = []; // Variantes de idioma de la carta actual
  
  selectedVarianteUniversalId: string | null = null; // El ID único de la Variante seleccionada (carta+idioma)
  cantidadSeleccionada = 1; // Cantidad por defecto

  isLoading = false;
  error: string | null = null;

  constructor(private cartaBackendService: CartaBackendService) {}

  ngOnInit(): void {
    if (this.multiversalId) {
      this.cargarVariantesDeCarta();
    } else {
      this.error = "No se proporcionó un ID de carta para cargar sus variantes.";
    }
  }

  cargarVariantesDeCarta(): void {
    this.isLoading = true;
    this.error = null;
    this.cartaBackendService.getEdicionDetalle(this.multiversalId).subscribe({
      next: (detalle) => {
        this.edicionDetalle = detalle;
        if (detalle && detalle.variantes && detalle.variantes.length > 0) {
          this.variantesDisponibles = detalle.variantes;
          // Preseleccionar la primera variante disponible si hay
          if (this.variantesDisponibles.length > 0) {
            this.selectedVarianteUniversalId = this.variantesDisponibles[0].universalId;
          }
        } else {
          this.error = `No se encontraron variantes para la carta "${this.nombreBaseCarta}".`;
          this.variantesDisponibles = [];
        }
        this.isLoading = false;
      },
      error: (err) => {
        console.error(`Error al cargar variantes para ${this.multiversalId}:`, err);
        this.error = `Error al cargar las variantes de la carta "${this.nombreBaseCarta}".`;
        this.isLoading = false;
      }
    });
  }

  confirmarSeleccion(): void {
    if (!this.selectedVarianteUniversalId || this.cantidadSeleccionada <= 0) {
      // Podrías añadir una validación más específica aquí si es necesario
      this.error = "Por favor, selecciona una variante y una cantidad válida (mayor que 0).";
      return;
    }

    const varianteElegida = this.variantesDisponibles.find(
      v => v.universalId === this.selectedVarianteUniversalId
    );

    if (varianteElegida) {
      const seleccion: VarianteEnColeccion = new VarianteEnColeccion(
        varianteElegida.universalId, // Este es el ID único de la variante (carta+idioma)
        this.cantidadSeleccionada,
        varianteElegida.nombre,   // Nombre de la variante específica
        varianteElegida.imagen,   // Imagen de la variante específica
        // idiomaCodigo no es necesario almacenarlo en VarianteEnColeccion si el objeto Variante ya lo tiene y lo recuperaremos
        // pero el modal sí lo usa para mostrar, así que ya está en varianteElegida.idiomaCodigo
      );
      this.cerrarModal.emit(seleccion);
    } else {
      this.error = "Error al procesar la selección. Variante no encontrada.";
      // Esto no debería ocurrir si selectedVarianteUniversalId está bien poblado
    }
  }

  cancelar(): void {
    this.cerrarModal.emit(undefined); // Emitir undefined para indicar cancelación
  }

  // Para evitar que el clic dentro del modal lo cierre si el overlay tiene un (click)
  stopPropagation(event: Event): void {
    event.stopPropagation();
  }
}