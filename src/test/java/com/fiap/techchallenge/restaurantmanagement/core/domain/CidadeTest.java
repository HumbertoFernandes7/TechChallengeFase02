package com.fiap.techchallenge.restaurantmanagement.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CidadeTest {

    @Test
    void quando_criarCidadeRetornaSucesso(){
        assertDoesNotThrow(() -> {
            new Cidade(1L, "São Paulo", new Estado());
        });
    }
}