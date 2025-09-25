package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.EstadoEntity;
import org.springframework.stereotype.Component;


@Component
public class EstadoMapper {
    private final CidadeMapper cidadeMapper = new CidadeMapper();

    public Estado toDomain(EstadoEntity estadoEntity) {
        if (estadoEntity == null) {
            return null;
        }
        //TODO: melhorar os null
        return new Estado(
                estadoEntity.getId(),
                estadoEntity.getNome(),
                estadoEntity.getSigla(),
                estadoEntity.getCidades());
    }

    public EstadoEntity toEntity(Estado estado) {
        if (estado == null) {
            return null;
        }
        return new EstadoEntity(estado.getId(), estado.getNome(), estado.getSigla(), estado.getCidades());
    }
}
