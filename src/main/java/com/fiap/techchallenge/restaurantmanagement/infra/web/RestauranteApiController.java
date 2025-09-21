package com.fiap.techchallenge.restaurantmanagement.infra.web;

import com.fiap.techchallenge.restaurantmanagement.application.usecase.restaurante.*;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario.FindUsuarioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Restaurante;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.RestauranteRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.RestauranteResponse;
import com.fiap.techchallenge.restaurantmanagement.infra.web.mapper.RestauranteWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurante")
public class RestauranteApiController implements IRestauranteApiController {

    private final CreateRestauranteUseCase createRestauranteUseCase;
    private final FindRestauranteUseCase findRestauranteUseCase;
    private final UpdateRestauranteUseCase updateRestauranteUseCase;
    private final DeleteRestauranteUseCase deleteRestauranteUseCase;
    private final ListRestauranteUseCase listRestauranteUseCase;
    private final RestauranteWebMapper restauranteWebMapper;

    private final FindUsuarioUseCase findUsuarioUseCase;

    @Override
    public ResponseEntity<RestauranteResponse> create(RestauranteRequest restauranteRequest) {
        Usuario usuario = findUsuarioUseCase.execute(restauranteRequest.getDonoRestauranteId());
        Restaurante restaurante = restauranteWebMapper.toDomain(restauranteRequest, usuario);
        Restaurante restauranteSalvo = createRestauranteUseCase.execute(restaurante);
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteWebMapper.toResponse(restauranteSalvo));
    }

    @Override
    public ResponseEntity<RestauranteResponse> getById(Long id) {
        Restaurante restaurante = findRestauranteUseCase.execute(id);
        return ResponseEntity.ok(restauranteWebMapper.toResponse(restaurante));
    }

    @Override
    public ResponseEntity<List<RestauranteResponse>> listAll() {
        List<Restaurante> restaurantes = listRestauranteUseCase.execute();
        return ResponseEntity.ok(restauranteWebMapper.toResponseList(restaurantes));
    }

    @Override
    public ResponseEntity<RestauranteResponse> update(Long id, RestauranteRequest restauranteRequest) {
        Usuario donoRestaurante = findUsuarioUseCase.execute(restauranteRequest.getDonoRestauranteId());
        Restaurante restaurante = restauranteWebMapper.toDomain(restauranteRequest, donoRestaurante);
        Restaurante restauranteAtualizado = updateRestauranteUseCase.execute(id, restaurante);
        return ResponseEntity.ok(restauranteWebMapper.toResponse(restauranteAtualizado));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        deleteRestauranteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}