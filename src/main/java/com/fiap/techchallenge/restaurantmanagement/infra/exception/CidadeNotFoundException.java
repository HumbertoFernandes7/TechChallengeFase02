package com.fiap.techchallenge.restaurantmanagement.infra.exception;

public class CidadeNotFoundException extends RuntimeException {
    public CidadeNotFoundException(String message) {
        super(message);
    }
}
