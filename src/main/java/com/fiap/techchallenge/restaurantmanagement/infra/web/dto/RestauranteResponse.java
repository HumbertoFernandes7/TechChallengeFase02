package com.fiap.techchallenge.restaurantmanagement.infra.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class RestauranteResponse {

    private Long id;

    private String nome;

    private String endereco;

    private String tipoCozinha;

    private LocalTime horarioAbertura;

    private LocalTime horarioFechamento;

    private UsuarioResponse donoRestaurante;

}