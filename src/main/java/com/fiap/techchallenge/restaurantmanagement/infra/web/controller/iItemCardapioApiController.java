package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;


import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.ItemCardapioRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.ItemCardapioResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public interface iItemCardapioApiController {

    @GetMapping
    ResponseEntity<List<ItemCardapioResponse>> get();

    @GetMapping("/{id}")
    ResponseEntity<ItemCardapioResponse> get(@PathVariable Long id);

    @PostMapping
    ResponseEntity<ItemCardapioResponse> insert(@RequestBody @Valid ItemCardapioRequest itemCardapio);

    @PutMapping("/{id}")
    ResponseEntity<ItemCardapioResponse> update(@PathVariable Long id, @RequestBody @Valid ItemCardapioRequest itemCardapio);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
