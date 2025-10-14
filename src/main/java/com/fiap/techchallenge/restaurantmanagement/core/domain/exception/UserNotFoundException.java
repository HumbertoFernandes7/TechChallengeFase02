package com.fiap.techchallenge.restaurantmanagement.core.domain.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
