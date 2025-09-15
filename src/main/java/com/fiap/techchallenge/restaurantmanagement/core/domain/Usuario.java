package com.fiap.techchallenge.restaurantmanagement.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private Long id;

    private String nome;

    private String email;

    private TipoUsuario tipo;

    private String senha;

    private Endereco endereco;
}
