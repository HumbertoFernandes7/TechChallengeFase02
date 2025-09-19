package com.fiap.techchallenge.restaurantmanagement.infra.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteResponse {

    private Long id;

    private String nome;

    private String tipoCozinha;

    private String horarioAbertura;

    private String horarioFechamento;

    private UsuarioResponse donoRestaurante;

    private String cardapio;

}