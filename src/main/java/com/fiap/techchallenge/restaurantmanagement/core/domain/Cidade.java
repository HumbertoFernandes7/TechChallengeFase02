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
public class Cidade {
    private Long id;
    private String nome;
    private Estado estado;
    private List<Endereco> enderecos;
}
