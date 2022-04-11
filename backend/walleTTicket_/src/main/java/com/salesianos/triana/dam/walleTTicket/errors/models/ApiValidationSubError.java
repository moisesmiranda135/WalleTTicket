package com.salesianos.triana.dam.walleTTicket.errors.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiValidationSubError extends ApiSubError {
    //objeto, campo, mensaje;
    private String objet, field, message;
    // valorRechazado;
    private Object refusedValue;


}
