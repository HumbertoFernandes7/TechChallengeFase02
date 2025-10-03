package com.fiap.techchallenge.restaurantmanagement.infra.web.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
    private final List<String> errors;
}