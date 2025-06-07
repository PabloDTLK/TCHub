export class VarianteEnColeccion {
    varianteUniversalId: string;
    cantidad: number;
    nombreCarta?: string;
    imagenCarta?: string;
    idiomaCodigo?: string;

    constructor(
        varianteUniversalId: string,
        cantidad: number,
        nombreCarta?: string,
        imagenCarta?: string,
        idiomaCodigo?: string
    ) {
        this.varianteUniversalId = varianteUniversalId;
        this.cantidad = cantidad;
        this.nombreCarta = nombreCarta;
        this.imagenCarta = imagenCarta;
        this.idiomaCodigo = idiomaCodigo;
    }
}