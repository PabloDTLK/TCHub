export class UsuarioBusqueda {
    username: string;
    isAdmin: boolean;
    activo: boolean;

    constructor(
        username: string,
        isAdmin: boolean,
        activo: boolean
    ) {
        this.username = username;
        this.isAdmin = isAdmin;
        this.activo = activo;
    }
}
