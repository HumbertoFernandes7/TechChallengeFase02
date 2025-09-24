package com.fiap.techchallenge.restaurantmanagement.infra.web;

import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.UsuarioResponse;
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

    @PutMapping
    ResponseEntity<Void> update(@PathVariable Long id,@RequestBody @Valid T t);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
