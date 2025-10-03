package com.fiap.techchallenge.restaurantmanagement.infra.exception;

public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException(String message) {
        super(message);
    }
}
