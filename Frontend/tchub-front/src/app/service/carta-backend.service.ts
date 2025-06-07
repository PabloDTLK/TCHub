import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EdicionDetalle } from '../model/edicionDetalle';
import { environment } from '../../environments/environment.prod';
import { Observable } from 'rxjs';
import { EdicionPreview } from '../model/edicionPreview';
import { Variante } from '../model/variante';


@Injectable({
  providedIn: 'root'
})
export class CartaBackendService {

  private baseUrl = environment.baseUrlApi;

  constructor(private http: HttpClient) { }

  getEdicionDetalle(multiversalId:string): Observable<EdicionDetalle> {
    const url = `${this.baseUrl}/edicion/detalle/${multiversalId}`;
    return this.http.get<EdicionDetalle>(url);
  } 

  getEdicionPreview(multiversalId:string, idiomaCodigo:string): Observable<EdicionPreview> {
    const url = `${this.baseUrl}/edicion/preview/${multiversalId}/idioma/${idiomaCodigo}`;
    return this.http.get<EdicionPreview>(url);
  }

  getLista(longitud: number, pagina: number): Observable<string[]> {
    const url = `${this.baseUrl}/edicion/lista?longitud=${longitud}&pagina=${pagina}`;
    return this.http.get<string[]>(url);
  }

  getListaBusquedaNombre(nombre:string, idioma:string, longitud: number, pagina: number): Observable<string[]> {
    const url = `${this.baseUrl}/edicion/nombre/${idioma}?longitud=${longitud}&pagina=${pagina}&nombre=${nombre}`;
    return this.http.get<string[]>(url);
  }

  getVarianteDetalleById(universalId: string): Observable<Variante> {
    const url = `${this.baseUrl}/variante/universalId/${universalId}`;
    return this.http.get<Variante>(url);
  } 
  
}
