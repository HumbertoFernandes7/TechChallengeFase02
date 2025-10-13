package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public interface IEstadoApiController<T> {

    @GetMapping
    ResponseEntity<List<T>> listAll();

    @GetMapping("/{id}")
    ResponseEntity<T> get(@PathVariable Long id);

    @PostMapping
    ResponseEntity<Void> insert(@RequestBody @Valid T t);

    @PutMapping()
    ResponseEntity<Void> update(@RequestBody @Valid T t);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
