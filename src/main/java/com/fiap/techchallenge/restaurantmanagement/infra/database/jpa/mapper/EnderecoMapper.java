package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    public Endereco toDomain(EnderecoEntity enderecoEntity) {
        if (enderecoEntity == null) {
            return null;
        }
        //TODO: melhorar os null
        return new Endereco(
                enderecoEntity.getId(),
                enderecoEntity.getLogradouro(),
                enderecoEntity.getNumero(),
                enderecoEntity.getComplemento(),
                enderecoEntity.getCep(),
                enderecoEntity.getBairro(),
                null);
    }

    public EnderecoEntity toEntity(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return new EnderecoEntity(endereco.getId(), endereco.getLogradouro(), endereco.getNumero(), endereco.getComplemento(), endereco.getCep(), endereco.getBairro(), null);
    }


}
