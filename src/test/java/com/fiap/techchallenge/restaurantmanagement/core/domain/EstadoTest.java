package com.fiap.techchallenge.restaurantmanagement.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class EstadoTest {

    @Test
    void quando_criarEstadoRetornaSucesso(){
        assertDoesNotThrow(() -> {
            new Estado(1L, "São Paulo", "SP");
        });
    }
}