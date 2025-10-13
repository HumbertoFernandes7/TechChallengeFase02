package com.fiap.techchallenge.restaurantmanagement.infra.web.controller;

import com.fiap.techchallenge.restaurantmanagement.application.usecase.cidade.*;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
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
@RequestMapping("/cidade")
@RequiredArgsConstructor
public class CidadeApiController implements ICidadeApiController<Cidade> {
    private final CreateCidadeUseCase createCidadeUseCase;
    private final DeleteCidadeUseCase deleteCidadeUseCase;
    private final FindCidadeUseCase findCidadeUseCase;
    private final ListCidadeUseCase listCidadeUseCase;
    private final UpdateCidadeUseCase updateCidadeUseCase;

    @Override
    public ResponseEntity<List<Cidade>> listAll() {
        return ResponseEntity.ok().body(listCidadeUseCase.execute());
    }

    @Override
    public ResponseEntity<Cidade> get(Long id) {
        return ResponseEntity.ok().body(findCidadeUseCase.get(id));
    }

    @Override
    public ResponseEntity<Void> insert(Cidade cidade) {
        cidade = createCidadeUseCase.saveCidade(cidade);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cidade.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<Void> update(Cidade cidade) {
        cidade = updateCidadeUseCase.execute(cidade);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + cidade.getId()).buildAndExpand(cidade.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        deleteCidadeUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
