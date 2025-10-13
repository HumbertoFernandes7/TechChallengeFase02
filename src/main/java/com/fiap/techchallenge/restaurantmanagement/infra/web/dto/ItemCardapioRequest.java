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

    @NotBlank(message = "descrição é obrigatória")
    private String descricao;

    @NotNull(message = "preço é obrigatório")
    private BigDecimal preco;

    @NotNull(message = "disponibilidade é obrigatória")
    private boolean disponibilidade;

    @NotBlank(message = "foto é obrigatória")
    private String fotoPrato;

    private Long cardapioId;
}
