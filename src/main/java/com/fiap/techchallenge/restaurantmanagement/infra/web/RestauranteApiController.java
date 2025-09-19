package com.fiap.techchallenge.restaurantmanagement.infra.web;

import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.RestauranteRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.RestauranteResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurante")
public class RestauranteApiController implements IRestauranteApiController {

    @Override
    public ResponseEntity<RestauranteResponse> create(RestauranteRequest restauranteRequest) {
        return null;
    }

    @Override
    public ResponseEntity<RestauranteResponse> getById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<RestauranteResponse>> listAll() {
        return null;
    }

    @Override
    public ResponseEntity<RestauranteResponse> update(Long id, RestauranteRequest restauranteRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return null;
    }
}