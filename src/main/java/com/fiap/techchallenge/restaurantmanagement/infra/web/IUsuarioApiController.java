package com.fiap.techchallenge.restaurantmanagement.infra.web;

import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.UsuarioRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.UsuarioResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IUsuarioApiController {

    @PostMapping
    ResponseEntity<UsuarioResponse> create(@RequestBody UsuarioRequest usuarioRequest);

    @GetMapping("/{id}")
    ResponseEntity<UsuarioResponse> getById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<UsuarioResponse>> listAll();

    @PutMapping("/{id}")
    ResponseEntity<UsuarioResponse> update(@PathVariable Long id, @RequestBody UsuarioRequest request);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}