package com.fiap.techchallenge.restaurantmanagement.infra.exception;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
