import { Component, OnInit, OnDestroy, EventEmitter, Output, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Observable, of, Subject, Subscription } from 'rxjs';
import { catchError, tap, debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { ListCartaComponent } from '../display/list-carta/list-carta.component';
import { CartaBackendService } from '../../service/carta-backend.service';

@Component({
  selector: 'app-catalogo',
  standalone: true,
  imports: [CommonModule, FormsModule, ListCartaComponent],
  templateUrl: './catalogo.component.html',
  styleUrls: ['./catalogo.component.css']
})
export class CatalogoComponent implements OnInit, OnDestroy {
  @Input() modoSeleccionParaEditor: boolean = false;
  @Output() cartaClickeada = new EventEmitter<string>();

  cartasIds: string[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 60;
  hasNextPage: boolean = false;

  isLoading: boolean = false;
  error: string | null = null;

  searchTerm: string = '';
  searchIdioma: string = 'ES';
  searchSubject = new Subject<string>();
  searchSubscription!: Subscription;
  lastManuallyTriggeredSearchTerm: string | null = null;

  constructor(private cartaBackendService: CartaBackendService) { }

  ngOnInit(): void {
    this.loadInitialData();

    this.searchSubscription = this.searchSubject.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      tap(term => {
        if (term !== this.lastManuallyTriggeredSearchTerm) {
          this.resetForNewSearch();
        }
        this.lastManuallyTriggeredSearchTerm = null;
      }),
      switchMap(term => {
        const trimmedTerm = term.trim();
        return this.fetchData(0, trimmedTerm);
      })
    ).subscribe();
  }

  ngOnDestroy(): void {
    if (this.searchSubscription) {
      this.searchSubscription.unsubscribe();
    }
  }

  loadInitialData(): void {
    this.fetchData(this.currentPage, '').subscribe();
  }

  onSearchTermChange(term: string): void {
    this.searchTerm = term;
    this.searchSubject.next(term);
  }

  triggerManualSearch(): void {
    if (this.isLoading) return;

    const termToSearch = this.searchTerm.trim();
    this.lastManuallyTriggeredSearchTerm = termToSearch;

    this.resetForNewSearch();
    this.fetchData(0, termToSearch).subscribe();
  }

  private resetForNewSearch(): void {
    this.isLoading = true;
    this.cartasIds = [];
    this.currentPage = 0;
    this.error = null;
    this.hasNextPage = false;
  }

  private fetchData(page: number, currentSearchTerm: string): Observable<string[] | null> {
    if (!this.isLoading) {
      this.isLoading = true;
    }

    let apiCall: Observable<string[]>;

    if (currentSearchTerm) {
      page = 0
      apiCall = this.cartaBackendService.getListaBusquedaNombre(currentSearchTerm, this.searchIdioma, this.itemsPerPage, page);
    } else {
      apiCall = this.cartaBackendService.getLista(this.itemsPerPage, page);
    }

    return apiCall.pipe(
      tap((response: string[]) => {
        this.cartasIds = response;
        this.currentPage = page;
        this.hasNextPage = response.length === this.itemsPerPage;
        this.isLoading = false;

        if (response.length === 0 && page === 0) {
          this.error = currentSearchTerm
            ? `No se encontraron cartas para "${currentSearchTerm}".`
            : "No hay cartas disponibles en el catálogo en este momento.";
        } else {
          this.error = null;
        }
      }),
      catchError(err => {
        console.error('Error loading cartas:', err);
        this.error = 'No se pudieron cargar las cartas. Inténtalo de nuevo más tarde.';
        this.isLoading = false;
        this.cartasIds = [];
        this.hasNextPage = false;
        return of(null);
      })
    );
  }

  paginate(page: number): void {
    if (this.isLoading) return;

    this.fetchData(page, this.searchTerm.trim()).subscribe();
  }

  nextPage(): void {
    if (this.hasNextPage && !this.isLoading) {
      this.paginate(this.currentPage + 1);
    }
  }

  previousPage(): void {
    if (this.currentPage > 0 && !this.isLoading) {
      this.paginate(this.currentPage - 1);
    }
  }

  onCartaSeleccionadaDeLista(multiversalId: string): void {
    this.cartaClickeada.emit(multiversalId);
  }
}