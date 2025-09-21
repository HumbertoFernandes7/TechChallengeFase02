package com.fiap.techchallenge.restaurantmanagement.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurante {

    private Long id;

    private String nome;

    private Endereco endereco;

    private String tipoCozinha;

    private LocalTime horarioAbertura;

    private LocalTime horarioFechamento;

    private Usuario donoRestaurante;

    private String cardapio;

    public Restaurante(String nome, String tipoCozinha, LocalTime horarioAbertura, LocalTime horarioFechamento, Usuario donoRestaurante) {
        this.nome = nome;
        this.tipoCozinha = tipoCozinha;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.donoRestaurante = donoRestaurante;
    }
}