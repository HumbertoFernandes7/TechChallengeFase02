package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.CidadeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class CidadeMapper {

    private final EstadoMapper estadoMapper;

    public Cidade toDomain(CidadeEntity cidadeEntity) {
        if (cidadeEntity == null) {
            return null;
        }
        //TODO: melhorar os null
        return new Cidade(
                cidadeEntity.getId(),
                cidadeEntity.getNome(),
                estadoMapper.toDomain(cidadeEntity.getEstado()));
    }

    public CidadeEntity toEntity(Cidade cidade) {
        if (cidade == null) {
            return null;
        }
        return new CidadeEntity(
                cidade.getId(),
                cidade.getNome(),
                estadoMapper.toEntity(cidade.getEstado()));
    }
}
