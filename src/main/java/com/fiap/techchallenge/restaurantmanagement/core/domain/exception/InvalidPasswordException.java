package com.fiap.techchallenge.restaurantmanagement.core.domain.exception;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
