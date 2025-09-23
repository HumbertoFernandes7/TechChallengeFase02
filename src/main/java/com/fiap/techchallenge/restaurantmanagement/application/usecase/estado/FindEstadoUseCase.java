package com.fiap.techchallenge.restaurantmanagement.application.usecase.estado;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper.EstadoMapper;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository.EstadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindEstadoUseCase {
    private final EstadoRepository estadoRepository;
    private final EstadoMapper estadoMapper;

    public Estado execute(Long id) {
        return estadoRepository.findById(id).map(estadoMapper::toDomain).orElseThrow(
                () -> new EntityNotFoundException("Estado não encontrado"));
    }
}
