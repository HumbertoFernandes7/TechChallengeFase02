package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.RestauranteEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class RestauranteMapper {

    public RestauranteEntity toEntity(Restaurante restaurante, UsuarioEntity usuarioEntity) {
        return new RestauranteEntity(
                restaurante.getId(),
                restaurante.getNome(),
                null,
                restaurante.getTipoCozinha(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                usuarioEntity,
                restaurante.getCardapio()
        );
    }

    public Restaurante toDomain(RestauranteEntity restauranteSalvo, Usuario usuario) {
        return new Restaurante(
                restauranteSalvo.getId(),
                restauranteSalvo.getNome(),
                null,
                restauranteSalvo.getTipoCozinha(),
                restauranteSalvo.getHorarioAbertura(),
                restauranteSalvo.getHorarioFechamento(),
                usuario,
                restauranteSalvo.getCardapio()
        );
    }
}