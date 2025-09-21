package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurante")
public class RestauranteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_restaurante_id")
    private EnderecoEntity endereco;

    private String tipoCozinha;

    private LocalTime horarioAbertura;

    private LocalTime horarioFechamento;

    @OneToOne
    @JoinColumn(name = "dono_restaurante_id")
    private UsuarioEntity donoRestaurante;

    private String cardapio;

}