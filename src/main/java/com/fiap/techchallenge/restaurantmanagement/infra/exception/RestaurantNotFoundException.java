package com.fiap.techchallenge.restaurantmanagement.infra.exception;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
