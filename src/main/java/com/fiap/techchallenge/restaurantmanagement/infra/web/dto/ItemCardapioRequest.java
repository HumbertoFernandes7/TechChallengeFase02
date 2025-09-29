package com.fiap.techchallenge.restaurantmanagement.infra.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemCardapioRequest {

    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @NotBlank(message = "descrição é obrigatoria")
    private String descricao;

    @NotNull(message = "preço é obrigatorio")
    private BigDecimal preco;

    @NotNull(message = "desponibilidade é obrigatoria")
    private boolean disponibilidade;

    @NotBlank(message = "foto é obrigatoria")
    private String fotoPrato;

    private Long cardapioId;
}
