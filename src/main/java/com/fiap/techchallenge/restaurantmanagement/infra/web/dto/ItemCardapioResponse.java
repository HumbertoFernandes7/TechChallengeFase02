package com.fiap.techchallenge.restaurantmanagement.infra.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemCardapioResponse {

    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private boolean disponibilidade;

    private String fotoPrato;

}