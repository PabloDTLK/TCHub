import { CommonModule } from '@angular/common';
import { Component, HostListener, Input, Output, EventEmitter, OnDestroy, Renderer2 } from '@angular/core'; // Añadir Output, EventEmitter
import { PreviewCartaComponent } from '../preview-carta/preview-carta.component';
// CartaBackendService no se usa directamente aquí para el click, así que se puede quitar si solo es para el modal
// import { CartaBackendService } from '../../../service/carta-backend.service';
import { DisplayCartaComponent } from "../display-carta/display-carta.component";

@Component({
  selector: 'app-list-carta',
  standalone: true,
  imports: [CommonModule, PreviewCartaComponent, DisplayCartaComponent],
  templateUrl: './list-carta.component.html',
  styleUrls: ['./list-carta.component.css'] // Corregido styleUrl a styleUrls
})
export class ListCartaComponent implements OnDestroy {

  @Input() cartas: string[] = [];
  @Input() modoEditor: boolean = false; // Nuevo Input para controlar comportamiento

  @Output() cartaSeleccionadaParaEditor = new EventEmitter<string>(); // Nuevo Output

  isModalOpen: boolean = false;
  selectedCartaIdForModal: string | null = null;

  constructor(
    private renderer: Renderer2
  ) { }

  handleCartaClick(multiversalId: string): void {
    if (this.modoEditor) {
      this.cartaSeleccionadaParaEditor.emit(multiversalId);
    } else {
      this.openCartaDetailModal(multiversalId);
    }
  }

  openCartaDetailModal(cartaId: string): void {
    
    this.selectedCartaIdForModal = cartaId;
    this.isModalOpen = true;
    this.renderer.addClass(document.body, 'modal-open-no-scroll');
  }

  closeCartaDetailModal(): void {
    this.isModalOpen = false;
    this.selectedCartaIdForModal = null;
    this.renderer.removeClass(document.body, 'modal-open-no-scroll');
  }

  @HostListener('document:keydown.escape', ['$event'])
  onKeydownHandler(event: KeyboardEvent) {
    if (this.isModalOpen && !this.modoEditor) { // Solo cerrar el modal interno si no estamos en modo editor
      this.closeCartaDetailModal();
    }
  }

  ngOnDestroy(): void {
    if (this.isModalOpen) {
      this.renderer.removeClass(document.body, 'modal-open-no-scroll');
    }
  }
}