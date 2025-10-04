package com.fiap.techchallenge.restaurantmanagement.core.domain.exception;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
