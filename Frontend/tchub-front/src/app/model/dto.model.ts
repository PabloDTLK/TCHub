
export interface ColeccionVariantesListDTO {
    varianteUniversalId: string;
    cantidad: number;
}

export interface ColeccionConVariantesDTO {
    id?: string;
    nombre: string;
    descripcion: string;
    listaVariantes: ColeccionVariantesListDTO[];
}