package com.fiap.techchallenge.restaurantmanagement.infra.web;

import com.fiap.techchallenge.restaurantmanagement.application.usecase.estado.*;
import com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario.CreateUsuarioUseCase;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Estado;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/estado")
@AllArgsConstructor
public class EstadoApiController implements IEstadoApiController<Estado> {

    private final CreateEstadoUseCase createEstadoUseCase;
    private final DeleteEstadoUseCase deleteEstadoUseCase;
    private final FindEstadoUseCase findEstadoUseCase;
    private final ListEstadoUseCase listEstadoUseCase;
    private final UpdateEstadoUseCase updateEstadoUseCase;

    @Override
    public ResponseEntity<List<Estado>> listAll() {

        return ResponseEntity.ok().body(listEstadoUseCase.execute());
    }

    @Override
    public ResponseEntity<Estado> get(Long id) {
        return ResponseEntity.ok(findEstadoUseCase.execute(id));
    }

    @Override
    public ResponseEntity<Void> insert(Estado estado) {
        estado = createEstadoUseCase.saveEstado(estado);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(estado.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<Void> update(Long id, Estado estado) {
        estado = updateEstadoUseCase.execute(id, estado);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + estado.getId()).buildAndExpand(estado.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        deleteEstadoUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
