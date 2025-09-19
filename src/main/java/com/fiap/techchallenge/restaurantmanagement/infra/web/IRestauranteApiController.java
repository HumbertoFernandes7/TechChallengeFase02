package com.fiap.techchallenge.restaurantmanagement.infra.web;

import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public interface IRestauranteApiController {

    @PostMapping
    ResponseEntity<RestauranteResponse> create(@RequestBody @Valid RestauranteRequest restauranteRequest);

    @GetMapping("/{id}")
    ResponseEntity<RestauranteResponse> getById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<RestauranteResponse>> listAll();

    @PutMapping("/{id}")
    ResponseEntity<RestauranteResponse> update(@PathVariable Long id, @RequestBody @Valid RestauranteRequest restauranteRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}