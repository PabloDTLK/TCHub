import { Component, Input, OnInit, ElementRef, Renderer2, HostListener, ViewChild, AfterViewInit } from '@angular/core';
import { CartaBackendService } from '../../../service/carta-backend.service';
import { CommonModule } from '@angular/common';
import { environment } from '../../../../environments/environment.prod';

@Component({
  selector: 'app-preview-carta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './preview-carta.component.html',
  styleUrl: './preview-carta.component.css'
})
export class PreviewCartaComponent implements OnInit, AfterViewInit {

  constructor(
    private cartaBackendService: CartaBackendService,
    private renderer: Renderer2
  ) { }

  @Input()
  multiversalId!: string;

  @Input()
  idiomaCodigo!: string;

  edicionPreview: any;

  imagenCarta: any;

  @ViewChild('cardContainerRef', { static: true }) cardContainerRef!: ElementRef<HTMLDivElement>;
  
  private readonly detailsPanelWidth = 200;
  private readonly detailsPanelPadding = 20 * 2;
  private readonly totalDetailsWidth = this.detailsPanelWidth + this.detailsPanelPadding;

  private imageHasErrored = false;


  private detailsWereOnLeft = false;

  ngOnInit() {

    this.cartaBackendService.getEdicionPreview(this.multiversalId, this.idiomaCodigo)
      .subscribe($edicionDetalle => {
        this.edicionPreview = $edicionDetalle;

        this.imagenCarta = $edicionDetalle.varianteImagen;
      })
  }

  ngAfterViewInit(): void {
    if (!this.cardContainerRef) {
      console.error('Referencia cardContainerRef no encontrada');
    }
  }

  onImageMouseEnter(): void {
    if (!this.cardContainerRef?.nativeElement) {
      console.warn('PreviewCartaComponent: cardContainerRef.nativeElement no disponible en onImageMouseEnter.');
      return;
    }

    const cardElement = this.cardContainerRef.nativeElement;
    const detailsElement = cardElement.querySelector('.card-details-section') as HTMLElement; // Obtener el panel de detalles
    if (!detailsElement) return;

    const rect = cardElement.getBoundingClientRect();
    const viewportWidth = window.innerWidth;
    const spaceNeededOnRight = rect.right + this.totalDetailsWidth;

    if (spaceNeededOnRight > viewportWidth && rect.left >= this.totalDetailsWidth) {
      this.renderer.addClass(cardElement, 'show-details-left');
      this.detailsWereOnLeft = true;
      this.renderer.removeClass(detailsElement, 'keep-left-while-closing');
    } else {
      this.renderer.removeClass(cardElement, 'show-details-left');
      this.detailsWereOnLeft = false;
      this.renderer.removeClass(detailsElement, 'keep-left-while-closing');
    }
  }

  onImageMouseLeave(): void {
    if (!this.cardContainerRef?.nativeElement) return;

    const cardElement = this.cardContainerRef.nativeElement;
    const detailsElement = cardElement.querySelector('.card-details-section') as HTMLElement;
    if (!detailsElement) return;

    if (this.detailsWereOnLeft) {
      this.renderer.addClass(detailsElement, 'keep-left-while-closing');

      setTimeout(() => {
        if (detailsElement) {
          this.renderer.removeClass(detailsElement, 'keep-left-while-closing');
        }
      }, 300);
    } else {

    }
  }

    handleImageError(): void {
    if (!this.imageHasErrored) {
      this.imageHasErrored = true;
      this.imagenCarta = environment.backCardImg[this.edicionPreview.juegoCodigo];
    }
  }

}
