package com.pablomr.TCHub.exception.handler;

import com.pablomr.TCHub.exception.ValidacionException;
import com.pablomr.TCHub.exception.dto.ValidacionExceptionDto;
import com.pablomr.TCHub.exception.utils.ErrorConstants;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Locale;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<ValidacionExceptionDto> handleValidacionExcepcion(ValidacionException ex) {

        System.err.println(ex.getMessage());

        String message = messageSource.getMessage(ex.getCodigo(), ex.getParams(), Locale.getDefault());
        ValidacionExceptionDto respuesta = new ValidacionExceptionDto(message, LocalDateTime.now(), HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInternalError(Exception ex, WebRequest request) {

        System.err.println(ex.getMessage());

        String message = messageSource.getMessage(ErrorConstants.ERROR_INTERNO, null, Locale.getDefault());

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
