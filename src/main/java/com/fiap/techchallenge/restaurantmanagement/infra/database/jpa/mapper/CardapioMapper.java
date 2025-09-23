package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.CardapioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CardapioMapper {

    private final RestauranteMapper restauranteMapper;
    private final ItemCardapioMapper itemCardapioMapper;

    public CardapioEntity toEntity(Cardapio cardapio) {
        CardapioEntity cardapioEntity = new CardapioEntity();
        cardapioEntity.setId(cardapio.getId());
        cardapioEntity.setRestaurante(restauranteMapper.toEntity(cardapio.getRestaurante()));
        return cardapioEntity;
    }

    public Cardapio toDomain(CardapioEntity cardapio) {
        return new Cardapio(
                cardapio.getId(),
                restauranteMapper.toDomain(cardapio.getRestaurante()),
                cardapio.getItensCardapio() != null ?
                        cardapio.getItensCardapio().stream()
                                .map(itemCardapioMapper::toDomain)
                                .collect(Collectors.toList()) :
                        Collections.emptyList()
        );
    }
}