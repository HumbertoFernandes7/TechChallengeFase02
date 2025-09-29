package com.fiap.techchallenge.restaurantmanagement.infra.web.mapper;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.UsuarioRequest;
import com.fiap.techchallenge.restaurantmanagement.infra.web.dto.UsuarioResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UsuarioWebMapper {

    private final ModelMapper modelMapper;

    public Usuario toDomain(UsuarioRequest usuarioRequest) {
        return new Usuario(
                null,
                usuarioRequest.getNome(),
                usuarioRequest.getEmail(),
                usuarioRequest.getTipo(),
                usuarioRequest.getSenha()
        );
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponse.class);
    }

    public List<UsuarioResponse> toResponseList(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toResponse).collect(Collectors.toList());
    }
}