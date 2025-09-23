package com.fiap.techchallenge.restaurantmanagement.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Estado {
    private Long id;
    private String nome;
    private String sigla;
    private List<Cidade> cidades;
}
