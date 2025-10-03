package com.fiap.techchallenge.restaurantmanagement.core.domain.exception;

public class CidadeNotFoundException extends RuntimeException {
    public CidadeNotFoundException(String message) {
        super(message);
    }
}
