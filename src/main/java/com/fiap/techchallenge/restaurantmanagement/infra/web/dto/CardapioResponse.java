package com.fiap.techchallenge.restaurantmanagement.infra.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CardapioResponse {

    private Long id;

    private List<ItemCardapioResponse> itensCardapio;
}
