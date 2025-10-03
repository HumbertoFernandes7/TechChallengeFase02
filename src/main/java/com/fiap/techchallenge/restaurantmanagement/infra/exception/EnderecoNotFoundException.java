package com.fiap.techchallenge.restaurantmanagement.infra.exception;

public class EnderecoNotFoundException extends RuntimeException {
    public EnderecoNotFoundException(String message) {
        super(message);
    }
}
