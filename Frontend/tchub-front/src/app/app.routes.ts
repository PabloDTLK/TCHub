import { Routes } from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { CatalogoComponent } from './components/catalogo/catalogo.component';
import { MisColeccionesComponent } from './components/colecciones/mis-colecciones/mis-colecciones.component';
import { authGuard } from './guards/auth.guard';
import { EditorColeccionComponent } from './components/colecciones/editor-coleccion/editor-coleccion.component';
import { PerfilComponent } from './components/perfil/perfil.component';
import { BuscadorUsuariosComponent } from './components/buscador-usuarios/buscador-usuarios.component';

export const routes: Routes = [

    { path: '', redirectTo: '/login', pathMatch: 'full' },
    // { path: '', redirectTo: '/catalogo', pathMatch: 'full' }, // Alternativa

    { path: 'login', component: LoginComponent, title: 'Iniciar Sesión' },
    { path: 'registro', component: RegisterComponent, title: 'Crear Cuenta' },
    { path: 'catalogo', component: CatalogoComponent, title: 'Catálogo' },

    {
        path: 'mis-colecciones', // Para las colecciones del usuario logueado
        component: MisColeccionesComponent,
        canActivate: [authGuard],
        title: 'Mis Colecciones'
        // No data especial aquí, el componente sabrá que es para el logueado si no hay :username
    },
    {
        path: 'mis-colecciones/:username', // Para ver/editar las colecciones de un usuario específico (para admins)
        component: MisColeccionesComponent,
        canActivate: [authGuard], // Aún necesita auth, y el componente decidirá si el admin puede ver esto
        title: 'Colecciones de Usuario' // El título se puede ajustar en el componente
    },
    {
        path: 'coleccion/nueva',
        component: EditorColeccionComponent,
        canActivate: [authGuard],
        title: 'Nueva Colección'
    },
    {
        path: 'coleccion/editar/:id',
        component: EditorColeccionComponent,
        canActivate: [authGuard],
        title: 'Editar Colección'
    },
    {
        path: 'perfil', // Si se accede directamente a /perfil, se asume el del usuario logueado
        component: PerfilComponent,
        canActivate: [authGuard], // Necesitas estar logueado para ver "tu" perfil por esta ruta
        title: 'Mi Perfil',
        data: { esMiPerfil: true } // Dato para indicar al componente que es el perfil del logueado
    },
    {
        path: 'perfil/:username', // Perfil específico por username
        component: PerfilComponent,
        title: 'Perfil de Usuario' // El título podría ser dinámico
        // No requiere authGuard aquí si los perfiles son públicos
    },
    {
        path: 'buscar-usuarios',
        component: BuscadorUsuariosComponent,
        canActivate: [authGuard], // Solo usuarios logueados
        title: 'Buscar Usuarios'
    },
    // { path: 'perfil', component: PerfilComponent, canActivate: [authGuard], title: 'Mi Perfil' },


    // LA RUTA WILDCARD SIEMPRE AL FINAL
    // Decide a dónde redirigir si una ruta no se encuentra
    { path: '**', redirectTo: '/catalogo' }
];
