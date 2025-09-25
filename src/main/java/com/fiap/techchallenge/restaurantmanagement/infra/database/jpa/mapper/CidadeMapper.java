package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.CidadeEntity;
import org.springframework.stereotype.Component;

@Component
public class CidadeMapper {

    public Cidade toDomain(CidadeEntity cidadeEntity) {
        if (cidadeEntity == null) {
            return null;
        }
        //TODO: melhorar os null
        return new Cidade(cidadeEntity.getId(),
                cidadeEntity.getNome(), cidadeEntity.getEstado(), cidadeEntity.getEnderecos());

    }

    public CidadeEntity toEntity(Cidade cidade) {
        if (cidade == null) {
            return null;
        }
        return new CidadeEntity(cidade.getId(), cidade.getNome(), cidade.getEstado(), cidade.getEnderecos());
    }
}
