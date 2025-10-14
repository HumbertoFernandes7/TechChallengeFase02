package com.fiap.techchallenge.restaurantmanagement.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class EnderecoTest {

    @Test
    void quando_criarEnderecoRetornaSucesso(){
        assertDoesNotThrow(() -> {
            new Endereco(1L, "Logradouro Teste", "123", "Casa 3", "Bairro Teste", "01020020", new Cidade());
        });
    }
}