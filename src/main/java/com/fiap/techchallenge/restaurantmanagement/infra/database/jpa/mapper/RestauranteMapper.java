package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.RestauranteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestauranteMapper {

    private final UsuarioMapper usuarioMapper;

    public RestauranteEntity toEntity(Restaurante restaurante) {
        return new RestauranteEntity(
                restaurante.getId(),
                restaurante.getNome(),
                null,
                restaurante.getTipoCozinha(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                usuarioMapper.toEntity(restaurante.getDonoRestaurante()),
                restaurante.getCardapio()
        );
    }

    public Restaurante toDomain(RestauranteEntity restauranteSalvo) {
        return new Restaurante(
                restauranteSalvo.getId(),
                restauranteSalvo.getNome(),
                null,
                restauranteSalvo.getTipoCozinha(),
                restauranteSalvo.getHorarioAbertura(),
                restauranteSalvo.getHorarioFechamento(),
                usuarioMapper.toDomain(restauranteSalvo.getDonoRestaurante()),
                restauranteSalvo.getCardapio()
        );
    }
}