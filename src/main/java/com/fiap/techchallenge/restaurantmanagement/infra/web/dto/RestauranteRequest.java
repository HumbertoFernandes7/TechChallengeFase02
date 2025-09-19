package com.fiap.techchallenge.restaurantmanagement.infra.web.dto;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Usuario;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteRequest {

    @NotBlank(message = "O nome do restaurante é obrigatório.")
    private String nome;

    @NotBlank(message = "O tipo de cozinha é obrigatório.")
    private String tipoCozinha;

    @NotBlank(message = "O horário de abertura é obrigatório.")
    private String horarioAbertura;

    @NotBlank(message = "O horário de fechamento é obrigatório.")
    private String horarioFechamento;

    @NotBlank(message = "Um usuario deve ser associado ao restaurante.")
    private Usuario donoRestaurante;

}