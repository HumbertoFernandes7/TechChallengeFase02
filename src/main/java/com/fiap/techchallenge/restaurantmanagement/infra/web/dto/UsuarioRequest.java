package com.fiap.techchallenge.restaurantmanagement.infra.web.dto;

import com.fiap.techchallenge.restaurantmanagement.core.domain.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {

    @NotBlank(message = "nome não pode ser nulo ou vazio")
    private String nome;

    @NotBlank(message = "email não pode ser nulo ou vazio")
    private String email;

    @NotNull(message = "tipo não pode ser nulo ou vazio")
    private TipoUsuario tipo;

    private String senha;

    @NotNull(message = "id do endereço não pode ser nulo")
    private Long enderecoId;
}
