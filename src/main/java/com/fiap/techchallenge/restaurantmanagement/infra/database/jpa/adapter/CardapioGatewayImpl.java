package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.CardapioGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cardapio;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.CardapioEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.CardapioMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.CardapioRepository;
import com.fiap.techchallenge.restaurantmanagement.infra.exception.CardapioNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CardapioGatewayImpl implements CardapioGateway {

    private final CardapioMapper cardapioMapper;
    private final CardapioRepository cardapioRepository;

    @Override
    public Cardapio save(Cardapio cardapio) {
       CardapioEntity cardapioEntity =  cardapioMapper.toEntity(cardapio);
       CardapioEntity cardapioSalvo = cardapioRepository.save(cardapioEntity);
       return cardapioMapper.toDomain(cardapioSalvo);
    }

    @Override
    public Cardapio findById(Long id) {
        return cardapioRepository.findById(id)
                .map(cardapioMapper::toDomain)
                .orElseThrow(() -> new CardapioNotFoundException("Cardápio com o id " + id + " não encontrado."));
    }

    @Override
    public List<Cardapio> findAll() {
        return cardapioRepository.findAll()
                .stream()
                .map(cardapioMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if(!cardapioRepository.existsById(id)){
           throw new CardapioNotFoundException("Cardápio com o id " + id + " não encontrado.");
        }
        cardapioRepository.deleteById(id);
    }
}