package com.fiap.techchallenge.restaurantmanagement.infra.web.dto;

import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private TipoUsuario tipo;
}
