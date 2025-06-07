package com.pablomr.TCHub.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ValidacionExceptionDto {

    private String message;
    private LocalDateTime time;
    private Integer status;
}
