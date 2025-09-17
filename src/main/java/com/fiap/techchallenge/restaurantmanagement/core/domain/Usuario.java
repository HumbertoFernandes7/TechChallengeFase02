package com.fiap.techchallenge.restaurantmanagement.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Usuario {

    private Long id;

    private String nome;

    private String email;

    private TipoUsuario tipo;

    private String senha;

    private Endereco endereco;

    public Usuario(Long id, String nome, String email, TipoUsuario tipo, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
        this.senha = validarSenha(senha);
    }

    private void vinculaEndereco(Endereco endereco){
        this.endereco = endereco;
    }


    private String validarSenha(String senha){
        if(senha == null || senha.length() < 8){
            throw new IllegalArgumentException("A senha deve ter no mínimo 8 caracteres");
        } else {
            return senha;
        }
    }
}