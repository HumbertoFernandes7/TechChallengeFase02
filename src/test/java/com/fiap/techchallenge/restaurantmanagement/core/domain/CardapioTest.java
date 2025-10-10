package com.fiap.techchallenge.restaurantmanagement.core.domain;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class CardapioTest {

    @Test
    void quando_criarCardapioComRestaurante_deveAtribuirCorretamente() {

        Restaurante restaurante = new Restaurante();

        Cardapio cardapio = new Cardapio(restaurante);


        assertNotNull(cardapio.getRestaurante());
        assertEquals("Restaurante Teste", cardapio.getRestaurante().getNome());
        assertNotNull(cardapio.getItensCardapio());
        assertTrue(cardapio.getItensCardapio().isEmpty());
    }

    @Test
    void quando_criarCardapioComTodosOsArgumentos_deveAtribuirCorretamente() {
        // Preparação
        Restaurante restaurante = new Restaurante();
        Cardapio cardapio = new Cardapio(1L, restaurante, new ArrayList<>());

        // Verificação
        assertEquals(1L, cardapio.getId());
        assertNotNull(cardapio.getRestaurante());
        assertNotNull(cardapio.getItensCardapio());
    }
}