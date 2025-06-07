import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.prod';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Coleccion } from '../model/coleccion';
import { ColeccionConVariantesDTO } from '../model/dto.model';

@Injectable({
  providedIn: 'root'
})
export class ColeccionService {

  private baseUrl = `${environment.baseUrlApi}/coleccion/variantes`;

  constructor(
    private http: HttpClient
  ) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('sessionToken');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  saveColeccion(coleccion: Coleccion): Observable<Coleccion> {
    if (coleccion.id) {
      const url = `${this.baseUrl}/${coleccion.id}`;
      return this.http.put<Coleccion>(url, coleccion, { headers: this.getAuthHeaders() });
    } else {
      const url = `${this.baseUrl}`;
      return this.http.post<Coleccion>(url, coleccion, { headers: this.getAuthHeaders() });
    }
  }

  saveColeccionConFormatoDTO(coleccionData: ColeccionConVariantesDTO): Observable<ColeccionConVariantesDTO | Coleccion> { // La respuesta podría ser el DTO o tu modelo Coleccion
    console.log(coleccionData);
    
    if (coleccionData.id) {
      const url = `${this.baseUrl}/${coleccionData.id}`;
      return this.http.put<ColeccionConVariantesDTO | Coleccion>(url, coleccionData, { headers: this.getAuthHeaders() });
    } else {
      const url = `${this.baseUrl}`;
      return this.http.post<ColeccionConVariantesDTO | Coleccion>(url, coleccionData, { headers: this.getAuthHeaders() });
    }
  }


  getColeccionesUsuario(): Observable<Coleccion[]> {
    const url = `${this.baseUrl}/usuario`;
    return this.http.get<Coleccion[]>(url, { headers: this.getAuthHeaders() });
  }

  getColeccionesPorUsername(username: string): Observable<Coleccion[]> {
    const url = `${this.baseUrl}/usuario/${username}`;
    return this.http.get<Coleccion[]>(url, { headers: this.getAuthHeaders() });
  }

  getColeccionById(id: string): Observable<Coleccion> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Coleccion>(url, { headers: this.getAuthHeaders() });
  }

  deleteColeccion(id: string): Observable<any> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete(url, { headers: this.getAuthHeaders() });
  }

}
