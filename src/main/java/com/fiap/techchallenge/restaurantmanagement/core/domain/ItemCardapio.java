package com.fiap.techchallenge.restaurantmanagement.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ItemCardapio {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean disponibilidade;
    private String fotoPrato;
}
