package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.adapter;

import com.fiap.techchallenge.restaurantmanagement.application.gateway.EstadoGateway;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.EstadoEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.EstadoMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.EstadoRepository;
import com.fiap.techchallenge.restaurantmanagement.core.domain.exception.EstadoNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EstadoGatewayImpl implements EstadoGateway {

    private final EstadoRepository estadoRepository;
    private final EstadoMapper estadoMapper;

    @Override
    public Estado save(Estado estado) {
        EstadoEntity estadoEntity = estadoMapper.toEntity(estado);
        EstadoEntity estadoEntitySalvo = estadoRepository.save(estadoEntity);
        return estadoMapper.toDomain(estadoEntitySalvo);
    }

    @Override
    public Estado update(Long id, Estado estadoAtualizado) {
        EstadoEntity estadoEntity = estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNotFoundException("Estado com o id " + id + " não encontrado."));

        estadoEntity.setNome(estadoAtualizado.getNome());
        estadoEntity.setSigla(estadoAtualizado.getSigla());

        EstadoEntity estadoSalvo = estadoRepository.save(estadoEntity);
        return estadoMapper.toDomain(estadoSalvo);
    }

    @Override
    public Estado findById(Long id) {
        return estadoRepository.findById(id).map(estadoMapper::toDomain).orElseThrow(
                () -> new EstadoNotFoundException("Estado com o id " + id + " não encontrado."));
    }

    @Override
    public List<Estado> findAll() {
        return  estadoRepository.findAll()
                .stream()
                .map(estadoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!estadoRepository.existsById(id)) {
            throw new EstadoNotFoundException("Estado com o id " + id + " não encontrado.");
        }
        estadoRepository.deleteById(id);
    }
}
