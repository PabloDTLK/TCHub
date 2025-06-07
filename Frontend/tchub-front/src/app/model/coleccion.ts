// src/app/model/coleccion.ts
import { VarianteEnColeccion } from "./varianteEnColeccion";

export interface VarianteBaseEnColeccionDTO { // Lo que viene del backend en la lista
    varianteUniversalId: string;
    cantidad: number;
}

export class Coleccion {
    id?: string;
    nombre: string;
    descripcion: string;
    // Esta es la propiedad que tu backend probablemente devuelve (DTO)
    // o que tu ColeccionService devuelve después de una primera transformación.
    listaVariantes?: VarianteBaseEnColeccionDTO[]; // Nombre que coincide con tu DTO de backend

    // Esta propiedad la usaremos en el frontend DESPUÉS de enriquecer los datos.
    // El template de Perfil/Editor iterará sobre esta.
    variantesUI?: VarianteEnColeccion[]; // Para los datos enriquecidos

    usuarioId?: string;

    constructor(
        nombre: string,
        descripcion: string,
        // El constructor podría tomar el tipo base y las variantesUI ser undefined inicialmente
        listaVariantes?: VarianteBaseEnColeccionDTO[],
        id?: string
    ) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.listaVariantes = listaVariantes || [];
        this.variantesUI = []; // Se poblará después
    }
}