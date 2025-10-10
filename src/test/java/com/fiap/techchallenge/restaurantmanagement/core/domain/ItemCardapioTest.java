package com.fiap.techchallenge.restaurantmanagement.core.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ItemCardapioTest {

    @Test
    void quando_criarItemCardapioRetornaSucesso(){
        assertDoesNotThrow(() -> {
            new ItemCardapio(1L, "Item Teste", "Descrição do item", BigDecimal.valueOf(10.0), true, "foto.jpg");
        });
    }
}
