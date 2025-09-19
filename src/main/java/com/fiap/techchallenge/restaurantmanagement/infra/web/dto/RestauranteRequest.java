package com.fiap.techchallenge.restaurantmanagement.infra.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class RestauranteRequest {

    @NotBlank(message = "O nome do restaurante é obrigatório.")
    private String nome;

    @NotBlank(message = "O tipo de cozinha é obrigatório.")
    private String tipoCozinha;

    @NotNull(message = "O horário de abertura é obrigatório.")
    private LocalTime horarioAbertura;

    @NotNull(message = "O horário de fechamento é obrigatório.")
    private LocalTime horarioFechamento;

    @NotNull(message = "Um usuario deve ser associado ao restaurante.")
    private Long donoRestauranteId;
}