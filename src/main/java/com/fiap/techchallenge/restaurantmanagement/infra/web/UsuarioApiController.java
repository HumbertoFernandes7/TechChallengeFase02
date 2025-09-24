package com.fiap.techchallenge.restaurantmanagement.infra.web;

import com.fiap.techchallenge.restaurantmanagement.application.usecase.usuario.*;
import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.UsuarioRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.UsuarioResponse;
import com.fiap.techchallenge.restaurantmanagement.infra.web.mapper.UsuarioWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioApiController implements IUsuarioApiController {

    private final CreateUsuarioUseCase createUsuarioUseCase;
    private final FindUsuarioUseCase findUsuarioUseCase;
    private final UpdateUsuarioUseCase updateUsuarioUseCase;
    private final DeleteUsuarioUseCase deleteUsuarioUseCase;
    private final ListUsuarioUseCase listUsuarioUseCase;
    private final UsuarioWebMapper usuarioWebMapper;

    @Override
    public ResponseEntity<UsuarioResponse> create(UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioWebMapper.toDomain(usuarioRequest);
        Usuario usuarioSalvo = createUsuarioUseCase.execute(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioWebMapper.toResponse(usuarioSalvo));
    }

    @Override
    public ResponseEntity<UsuarioResponse> getById(Long id) {
        Usuario usuario = findUsuarioUseCase.execute(id);
        return ResponseEntity.ok(usuarioWebMapper.toResponse(usuario));
    }

    @Override
    public ResponseEntity<List<UsuarioResponse>> listAll() {
        List<Usuario> usuarios = listUsuarioUseCase.execute();
        return ResponseEntity.ok(usuarioWebMapper.toResponseList(usuarios));
    }

    @Override
    public ResponseEntity<UsuarioResponse> update(Long id, UsuarioRequest usuarioRequest) {
        Usuario usuarioParaAtualizar = usuarioWebMapper.toDomain(usuarioRequest);
        Usuario usuarioAtualizado = updateUsuarioUseCase.execute(id, usuarioParaAtualizar);
        return ResponseEntity.ok(usuarioWebMapper.toResponse(usuarioAtualizado));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        deleteUsuarioUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}