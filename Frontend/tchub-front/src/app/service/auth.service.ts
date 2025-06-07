import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.prod';
import { Credentials } from '../model/credentials';
import { LoginResponse } from '../model/loginResponse';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { RegisterResponse } from '../model/registerResponse';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { UsuarioBusqueda } from '../model/usuarioBusqueda';

interface DecodedToken {
  sub: string; // Subject, usualmente el username
  authorities: {authority: string}[];
  username: string;
  exp: number;
  iat: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  private baseUrl = environment.baseUrlApi;
  private loggedInStatus = new BehaviorSubject<boolean>(this.hasToken());
  
  constructor(private http: HttpClient, private router: Router) {}

  get isLoggedIn(): Observable<boolean> {
    return this.loggedInStatus.asObservable();
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('sessionToken');
  }
  
  login(credentials: Credentials): Observable<LoginResponse> {
    const url = `${this.baseUrl}/login`;
    return this.http.post<LoginResponse>(url, credentials)
      .pipe(
        tap(response => {
          console.log('AuthService: Login API response:', response);
          if (response && response.token) {
            localStorage.setItem('sessionToken', response.token);
            console.log('AuthService: Token stored, emitting loggedInStatus = true');
            this.loggedInStatus.next(true);
          } else {
            
            console.warn('AuthService: Login response successful but no token received.');
          }
        })
      );
  }


  register(dataToSend: Credentials): Observable<RegisterResponse> {
    const url = `${this.baseUrl}/usuario/registrar`;
    return this.http.post<LoginResponse>(url, dataToSend);
  }


  logout(): void {
    localStorage.removeItem('sessionToken');
    this.loggedInStatus.next(false);
    this.router.navigate(['/login']);
  }

  searchUsernames(term: string): Observable<UsuarioBusqueda[]> {
    const url = `${this.baseUrl}/usuario/search?term=${term}`;
    return this.http.get<UsuarioBusqueda[]>(url, { headers: this.getAuthHeaders() });
  }

  updateUserStatus(cambios :UsuarioBusqueda): Observable<UsuarioBusqueda> {
    const url = `${this.baseUrl}/usuario/status`;
    return this.http.put<UsuarioBusqueda>(url, cambios, { headers: this.getAuthHeaders() });
  }

  getCurrentUserRole(): string | null {
    const decodedToken = this.decodeToken();
    if (decodedToken && decodedToken.authorities[0].authority) {
      const authority = decodedToken.authorities[0].authority;
      
      if (authority.includes('ADMIN')) {
        console.log('[AuthService] - Rol determinado: ADMINISTRADOR');
        return 'ADMIN';
      }
      if (authority.includes('USER')) {
        console.log('[AuthService] - Rol determinado: USUARIO');
        return 'USER';
      }
    }
    console.warn('[AuthService] - No se pudo determinar el rol del token o no hay authorities:', decodedToken);
    return null;
  }

  
  getCurrentUsername(): string | null {
    const decodedToken = this.decodeToken();
    
    return decodedToken ? (decodedToken.username || decodedToken.sub) : null;
  }

  private decodeToken(): DecodedToken | null {
    const token = localStorage.getItem('sessionToken');
    if (token) {
      try {
        return jwtDecode<DecodedToken>(token);
      } catch (error) {
        console.error("Error decodificando el token:", error);
        this.logout();
        return null;
      }
    }
    return null;
  }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('sessionToken');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

}
