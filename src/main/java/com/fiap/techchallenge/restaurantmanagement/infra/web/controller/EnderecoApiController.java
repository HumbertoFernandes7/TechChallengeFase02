package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import com.fiap.techchallenge.restaurantmanagement.application.usecase.endereco.*;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Endereco;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/endereco")
@RequiredArgsConstructor
public class EnderecoApiController implements IEnderecoApiController<Endereco> {

    private final CreateEnderecoUseCase createEnderecoUseCase;
    private final DeleteEnderecoUseCase deleteEnderecoUseCase;
    private final FindEnderecoUseCase findEnderecoUseCase;
    private final ListEnderecoUseCase listEnderecoUseCase;
    private final UpdateEnderecoUseCase updateEnderecoUseCase;

    @Override
    public ResponseEntity<List<Endereco>> listAll() {
        return ResponseEntity.ok().body(listEnderecoUseCase.findEndereco());
    }

    @Override
    public ResponseEntity<Endereco> get(Long id) {
        return ResponseEntity.ok().body(findEnderecoUseCase.findEndereco(id));
    }

    @Override
    public ResponseEntity<Void> insert(Endereco endereco) {
        endereco = createEnderecoUseCase.saveEndereco(endereco);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(endereco.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<Void> update(Long id, Endereco endereco) {
        endereco = updateEnderecoUseCase.execute(id, endereco);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + endereco.getId()).build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        deleteEnderecoUseCase.deleteEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
