package com.fiap.techchallenge.restaurantmanagement.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestauranteTest {

    @Test
    void quando_associarEndereco_deveAtribuirOEnderecoCorretamente(){
        // Preparação
        Restaurante restaurante = new Restaurante();
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua teste");

        // Ação
        restaurante.associarEndereco(endereco);

        // Verificação
        assertNotNull(restaurante.getEndereco());
        assertEquals("Rua teste", restaurante.getEndereco().getLogradouro());
    }

}
