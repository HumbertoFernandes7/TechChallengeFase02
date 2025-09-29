package com.fiap.techchallenge.restaurantmanagement.infra.web.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.CardapioResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CardapioWebMapper {

    private final ModelMapper modelMapper;
    private final RestauranteWebMapper restauranteWebMapper;

    public Cardapio toDomain(Restaurante restaurante) {
        return new Cardapio(restaurante);
    }

    public CardapioResponse toResponse(Cardapio cardapio) {
        return modelMapper.map(cardapio, CardapioResponse.class);
    }

    public List<CardapioResponse> toResponseList(List<Cardapio> cardapios) {
        return cardapios.stream().map(this::toResponse).toList();
    }
}