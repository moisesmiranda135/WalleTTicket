package com.salesianos.triana.dam.walleTTicket.errors.exceptions;

public class NotAuthorizationException extends RuntimeException {

    public NotAuthorizationException() {
        super(String.format("No puedes editar una ticket que no ha sido creado por ti"));
    }
}