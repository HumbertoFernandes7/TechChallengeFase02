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

    public ItemCardapioEntity toEntity(ItemCardapio itemCardapio) {
        ItemCardapioEntity itemCardapioEntity = new ItemCardapioEntity();
        itemCardapioEntity.setId(itemCardapio.getId());
        itemCardapioEntity.setNome(itemCardapio.getNome());
        itemCardapioEntity.setDescricao(itemCardapio.getDescricao());
        itemCardapioEntity.setPreco(itemCardapio.getPreco());
        itemCardapioEntity.setDisponibilidade(itemCardapio.isDisponibilidade());
        itemCardapioEntity.setFotoPrato(itemCardapio.getFotoPrato());
        return itemCardapioEntity;
    }
}
