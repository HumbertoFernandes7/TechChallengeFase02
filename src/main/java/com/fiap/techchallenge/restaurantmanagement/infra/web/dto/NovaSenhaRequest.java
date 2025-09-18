package com.fiap.techchallenge.restaurantmanagement.infra.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NovaSenhaRequest {

    @Size(min = 8, message = "senha deve ter no mínimo 8 caracteres")
    @NotBlank(message = "senha não pode ser nulo ou vazio")
    private String novaSenha;

    @Size(min = 8, message = "repetir nova senha deve ter no mínimo 8 caracteres")
    @NotBlank(message = "repetir nova senha não pode ser nulo ou vazio")
    private String repetirNovaSenha;
}