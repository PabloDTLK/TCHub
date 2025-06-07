package com.pablomr.TCHub.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidacionException extends RuntimeException {

    private String codigo;
    private String[] params;

    public ValidacionException(String mensaje) {
        super(mensaje);
    }

    public ValidacionException(String codigo, String... params) {
        super();
        this.codigo = codigo;
        this.params = params;
    }

}
