package com.fiap.techchallenge.restaurantmanagement.infra.web.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.RestauranteEntity;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.RestauranteRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.RestauranteResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RestauranteWebMapper {

    private final ModelMapper modelMapper;

    public Restaurante toDomain(RestauranteRequest restauranteRequest, Usuario usuario) {
        return new Restaurante(
                restauranteRequest.getNome(),
                restauranteRequest.getTipoCozinha(),
                restauranteRequest.getHorarioAbertura(),
                restauranteRequest.getHorarioFechamento(),
                usuario
                );
    }

    public RestauranteResponse toResponse(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteResponse.class);
    }
}