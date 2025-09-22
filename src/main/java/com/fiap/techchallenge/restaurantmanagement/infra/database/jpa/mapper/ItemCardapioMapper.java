package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.ItemCardapio;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.ItemCardapioEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemCardapioMapper {

    public ItemCardapio toDomain(ItemCardapioEntity itemCardapioEntity) {
        return new ItemCardapio(
                itemCardapioEntity.getId(),
                itemCardapioEntity.getNome(),
                itemCardapioEntity.getDescricao(),
                itemCardapioEntity.getPreco(),
                itemCardapioEntity.isDisponibilidade(),
                itemCardapioEntity.getFotoPrato()
        );
    }
}
