export class EdicionPreview {
    nombre: string;
    fechaLanzamiento: Date;
    autor: string;
    multiversalId: string;
    precio: number;
    juegoCodigo: string;
    varianteImagen: string;

    constructor(nombre: string, fechaLanzamiento: Date, autor: string, multiversalId: string, precio: number, juegoCodigo: string, varianteImagen: string) {
        this.nombre = nombre;
        this.fechaLanzamiento = fechaLanzamiento;
        this.autor = autor;
        this.multiversalId = multiversalId;
        this.precio = precio;
        this.juegoCodigo = juegoCodigo;
        this.varianteImagen = varianteImagen;
    }

}