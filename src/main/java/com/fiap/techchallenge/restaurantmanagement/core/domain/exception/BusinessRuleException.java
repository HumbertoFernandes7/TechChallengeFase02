package com.fiap.techchallenge.restaurantmanagement.core.domain.exception;

public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException(String message) {
        super(message);
    }
}
