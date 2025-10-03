package com.fiap.techchallenge.restaurantmanagement.core.domain;

import com.fiap.techchallenge.restaurantmanagement.infra.exception.InvalidPasswordException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    public void updateUsuario(String nome, String email, TipoUsuario tipo){
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }

    public void changePassword(String novaSenha){
        this.senha = validarSenha(novaSenha);
    }

    private String validarSenha(String senha){
        if(senha == null || senha.length() < 8){
            throw new InvalidPasswordException("A senha deve ter 8 ou mais caracteres");
        } else {
            return senha;
        }
    }
}