package com.fiap.techchallenge.restaurantmanagement.infra.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
