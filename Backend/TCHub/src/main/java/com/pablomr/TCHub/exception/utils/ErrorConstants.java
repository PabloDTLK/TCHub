package com.pablomr.TCHub.exception.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorConstants {

    //Errores generales
    public static final String ERROR_INTERNO = "error.interno";

    //Errores genericos
    public static final String ENTITY_ID_NO_ENCONTRADO = "error.entity.id.no.encontrado";

    //Juego
    public static final String JUEGO_ID_NOT_FOUND = "error.juego.id.no.encontrado";
    public static final String JUEGO_NOMBRE_NOT_FOUND = "error.juego.nombre.no.encontrado";

    //Edicion
    public static final String EDICION_MULTIVERSAL_ID_NOT_FOUND = "error.edicion.multiversalId.no.encontrado";

    //Variante
    public static final String VARIANTE_UNIVERSAL_ID_NOT_FOUND = "error.variante.universalId.no.encontrado";

    public static final String VARIANTE_NOT_FOUND_FOR_IDIOMA = "error.variante.no.encontrada.para.idioma";

    //Usuario
    public static final String USUARIO_USERNAME_NOT_EXISTS = "error.usuario.username.no.existe";

    public static final String USUARIO_USERNAME_EXISTS = "error.usuario.username.existente";

    //Coleccion
    public static final String COLECCION_NOMBRE_USUARIO_EXISTS = "error.coleccion.nombre.usuario.existente";

    public static final String COLECCION_USUARIO_NONE = "error.coleccion.usuario.no.tiene";


}
