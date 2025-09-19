package com.fiap.techchallenge.restaurantmanagement.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurante {

    private String nome;

    private Endereco endereco;

    private String tipoCozinha;

    private LocalTime horarioAbertura;

    private LocalTime horarioFechamento;

    private Usuario donoRestaurante;

    private String cardapio;
}