package com.fiap.techchallenge.restaurantmanagement.infra.web.dto;

import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {

    @NotBlank(message = "nome não pode ser nulo ou vazio")
    private String nome;

    @NotBlank(message = "email não pode ser nulo ou vazio")
    private String email;

    @NotBlank(message = "tipo não pode ser nulo ou vazio")
    private TipoUsuario tipo;

    @Size(min = 8, message = "senha deve ter no mínimo 8 caracteres")
    @NotBlank(message = "senha não pode ser nulo ou vazio")
    private String senha;
}
