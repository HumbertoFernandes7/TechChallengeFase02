package com.fiap.techchallenge.restaurantmanagement.infra.web.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.RestauranteRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.RestauranteResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RestauranteWebMapper {

    private final ModelMapper modelMapper;

    public Restaurante toDomain(RestauranteRequest restauranteRequest, Usuario donoRestaurante) {
        return new Restaurante(
                restauranteRequest.getNome(),
                restauranteRequest.getTipoCozinha(),
                restauranteRequest.getHorarioAbertura(),
                restauranteRequest.getHorarioFechamento(),
                donoRestaurante
                );
    }

    public RestauranteResponse toResponse(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteResponse.class);
    }

    public List<RestauranteResponse> toResponseList(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toResponse).toList();
    }
}