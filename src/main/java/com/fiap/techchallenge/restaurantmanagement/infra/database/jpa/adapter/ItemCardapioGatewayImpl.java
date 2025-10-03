package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.ItemCardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.core.domain.ItemCardapio;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.ItemCardapioEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.CardapioMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.ItemCardapioMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.ItemCardapioRepository;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.ItemCardapioNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemCardapioGatewayImpl implements ItemCardapioGateway {

    private final ItemCardapioRepository itemCardapioRepository;

    private final ItemCardapioMapper itemCardapioMapper;

    private final CardapioMapper cardapioMapper;

    @Override
    public ItemCardapio save(ItemCardapio itemCardapio, Cardapio cardapio) {
        ItemCardapioEntity itemCardapioEntity = itemCardapioMapper.toEntity(itemCardapio);
        itemCardapioEntity.setCardapio(cardapioMapper.toEntity(cardapio));
        ItemCardapioEntity itemCardapioSalvo = itemCardapioRepository.save(itemCardapioEntity);
        return itemCardapioMapper.toDomain(itemCardapioSalvo);
    }

    @Override
    public ItemCardapio update(Long id, ItemCardapio itemCardapioAtualizado) {
        ItemCardapioEntity itemCardapioEntity = itemCardapioRepository.findById(id)
                .orElseThrow(()-> new ItemCardapioNotFoundException("Item de cardápio com o id " + id + " não encontrado."));

        itemCardapioEntity.setNome(itemCardapioAtualizado.getNome());
        itemCardapioEntity.setDescricao(itemCardapioAtualizado.getDescricao());
        itemCardapioEntity.setPreco(itemCardapioAtualizado.getPreco());
        itemCardapioEntity.setDisponibilidade(itemCardapioAtualizado.isDisponibilidade());
        itemCardapioEntity.setFotoPrato(itemCardapioAtualizado.getFotoPrato());

        itemCardapioRepository.save(itemCardapioEntity);
        return itemCardapioMapper.toDomain(itemCardapioEntity);
    }

    @Override
    public ItemCardapio findById(Long id) {
        return itemCardapioRepository.findById(id).map(itemCardapioMapper::toDomain).orElseThrow(
                () -> new ItemCardapioNotFoundException("Item de cardápio com o id " + id + " não encontrado."));
    }

    @Override
    public List<ItemCardapio> findAll() {
        return itemCardapioRepository.findAll()
                .stream()
                .map(itemCardapioMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!itemCardapioRepository.existsById(id)) {
            throw new ItemCardapioNotFoundException("Item de cardápio com o id " + id + " não encontrado.");
        }
        itemCardapioRepository.deleteById(id);
    }
}
