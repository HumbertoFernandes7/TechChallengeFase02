package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.CardapioRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.CardapioResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public interface ICardapioController {

    @GetMapping
    ResponseEntity<List<CardapioResponse>> get();

    @GetMapping("/{id}")
    ResponseEntity<CardapioResponse> get(@PathVariable Long id);

    @PostMapping
    ResponseEntity<CardapioResponse> insert(@RequestBody @Valid CardapioRequest cardapio);

    @PutMapping
    ResponseEntity<CardapioResponse> update(@RequestBody @Valid CardapioRequest cardapio);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
