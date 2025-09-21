package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.RestauranteEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class RestauranteMapper {

    private final UsuarioMapper usuarioMapper;
    private final CardapioMapper cardapioMapper;

    public RestauranteMapper(UsuarioMapper usuarioMapper, @Lazy CardapioMapper cardapioMapper) {
        this.usuarioMapper = usuarioMapper;
        this.cardapioMapper = cardapioMapper;
    }

    public RestauranteEntity toEntity(Restaurante restaurante) {
        return new RestauranteEntity(
                restaurante.getId(),
                restaurante.getNome(),
                null,
                restaurante.getTipoCozinha(),
                restaurante.getHorarioAbertura(),
                restaurante.getHorarioFechamento(),
                usuarioMapper.toEntity(restaurante.getDonoRestaurante()),
                restaurante.getCardapio() != null ? cardapioMapper.toEntity(restaurante.getCardapio()) : null
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
                restauranteSalvo.getCardapio() != null ? cardapioMapper.toDomain(restauranteSalvo.getCardapio()) : null
        );
    }
}