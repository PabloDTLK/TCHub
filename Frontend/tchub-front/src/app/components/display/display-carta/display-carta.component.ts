import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CartaBackendService } from '../../../service/carta-backend.service';
import { Variante } from '../../../model/variante';
import { CommonModule } from '@angular/common';
import { environment } from '../../../../environments/environment.prod';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-display-carta',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './display-carta.component.html',
  styleUrl: './display-carta.component.css'
})
export class DisplayCartaComponent implements OnInit, OnChanges {


  constructor(private cartaBackendService: CartaBackendService) { }

  @Input()
  multiversalId!: string;

  @Input()
  idiomaCodigo!: string;

  edicionDetalle: any = null;

  variante: any = null;

  idiomasDisponibles: any[] = [];

  idiomasNombres: any;

  backImg: string = "";

  volteado: boolean = false;

  ngOnInit() {
    this.idiomasNombres = environment.idiomasEs;
  }

  ngOnChanges(changes: SimpleChanges): void {
    let needsDataLoad = false;

    if (changes['multiversalId'] && changes['multiversalId'].currentValue) {
      if (changes['multiversalId'].currentValue !== changes['multiversalId'].previousValue) {
        needsDataLoad = true;
      } else if (!this.edicionDetalle) { // Initial load, multiversalId set but no data yet
        needsDataLoad = true;
      }
    }
    
    if (needsDataLoad) {
      this.fetchEdicionDetalleAndSetVariante();
    } else if (changes['idiomaCodigo'] && changes['idiomaCodigo'].currentValue !== changes['idiomaCodigo'].previousValue && this.edicionDetalle) {
      // If parent changes idiomaCodigo (the @Input), and we already have card details
      this.idiomaCodigo = changes['idiomaCodigo'].currentValue; // Update internal model from @Input
      this.updateVarianteForCurrentIdioma();
    } else if (!this.edicionDetalle && this.multiversalId && this.idiomaCodigo) {
      // Fallback for initial simultaneous input setting
        this.fetchEdicionDetalleAndSetVariante();
    }
  }

  fetchEdicionDetalleAndSetVariante(): void {
    if (!this.multiversalId) return;

    this.cartaBackendService.getEdicionDetalle(this.multiversalId).subscribe({
      next: edicionDetalle => {
        this.edicionDetalle = edicionDetalle;
        this.variante = null; // Reset
        this.idiomasDisponibles = []; // Reset

        if (this.edicionDetalle && this.edicionDetalle.variantes) {
          const uniqueIdiomas = new Set<string>();
          this.edicionDetalle.variantes.forEach(($variante: Variante) => {
            if ($variante.idiomaCodigo) {
              uniqueIdiomas.add($variante.idiomaCodigo);
            }
          });
          this.idiomasDisponibles = Array.from(uniqueIdiomas).sort(); // Sort for consistent order

          // Ensure current idiomaCodigo is valid for this card, or default.
          if (this.idiomasDisponibles.length > 0) {
            if (!this.idiomasDisponibles.includes(this.idiomaCodigo)) {
              this.idiomaCodigo = this.idiomasDisponibles[0]; // Default to first available
            }
          } else {
            this.idiomaCodigo = ''; // No languages available
          }
          
          this.updateVarianteForCurrentIdioma();
          this.backImg = environment.backCardImg[this.edicionDetalle.juegoCodigo] || ''; // Default if juegoCodigo not found
        } else {
          this.handleDataError("Edicion details or variantes are missing.");
        }
      },
      error: err => {
        this.handleDataError(err);
      }
    });
  }

  updateVarianteForCurrentIdioma(): void {
    if (this.edicionDetalle && this.edicionDetalle.variantes && this.idiomaCodigo) {
      this.variante = this.edicionDetalle.variantes.find((v: Variante) => v.idiomaCodigo === this.idiomaCodigo);
      if (!this.variante) {
        console.warn(`Variante not found for idioma ${this.idiomaCodigo} in card ${this.multiversalId}.`);
      }
    } else {
      this.variante = null; // Reset if conditions aren't met
    }
    this.volteado = false; // Reset flip state when variant changes
  }

  onIdiomaChange(newIdiomaCodigo: string): void {
    this.updateVarianteForCurrentIdioma();
  }
  
  handleDataError(error: any): void {
    console.error("Error loading card data:", error);
    this.edicionDetalle = null;
    this.variante = null;
    this.idiomasDisponibles = [];
    this.backImg = ""; // Or a default error/placeholder image
    // You could also set a user-facing error message property here
  }


  cargarCarta() {

    this.cartaBackendService.getEdicionDetalle(this.multiversalId)
      .subscribe($edicionDetalle => {
        this.edicionDetalle = $edicionDetalle;

        this.variante = this.edicionDetalle.variantes.find((variante: Variante) => {
          return variante.idiomaCodigo == this.idiomaCodigo
        });

        this.edicionDetalle.variantes.forEach(($variante: Variante) => {
          this.idiomasDisponibles.push($variante.idiomaCodigo)
        });

        this.backImg = environment.backCardImg[this.edicionDetalle.juegoCodigo];
      })
  }

  

  voltear() {
    this.volteado = !this.volteado
  }
  
}
