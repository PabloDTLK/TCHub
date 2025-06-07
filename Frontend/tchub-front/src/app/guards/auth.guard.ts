import { CanActivateFn, Router } from '@angular/router';
import { map, take, tap } from 'rxjs';
import { AuthService } from '../service/auth.service';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  console.log('AuthGuard: Verificando acceso a', state.url);

  return authService.isLoggedIn.pipe(
    take(1), // Muy importante para que el guardián no se quede colgado esperando emisiones
    tap((isLoggedInValueFromObservable: boolean) => {
      // Log #2: ¿Qué valor está emitiendo el observable isLoggedIn ANTES del map?
      console.log('[AuthGuard] Valor recibido de authService.isLoggedIn:', isLoggedInValueFromObservable);
    }),
    map(isLoggedIn => {
      // Log #3: ¿Entra al map y cuál es el valor de isLoggedIn aquí?
      console.log('[AuthGuard] Dentro del map, isLoggedIn status:', isLoggedIn);
      if (isLoggedIn) {
        console.log('[AuthGuard] Acceso PERMITIDO a', state.url);
        return true;
      } else {
        console.log('[AuthGuard] Acceso DENEGADO a', state.url, '. Redirigiendo a /login.');
        router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
        return false;
      }
    })
  );

};

