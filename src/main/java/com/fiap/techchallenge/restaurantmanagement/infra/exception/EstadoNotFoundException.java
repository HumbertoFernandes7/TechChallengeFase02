package com.fiap.techchallenge.restaurantmanagement.infra.exception;

public class EstadoNotFoundException extends RuntimeException {
    public EstadoNotFoundException(String message) {
        super(message);
    }
}
