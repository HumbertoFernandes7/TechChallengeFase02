package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "item_cardapio")
public class ItemCardapioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private boolean disponibilidade;

    private String fotoPrato;

    @ManyToOne
    @JoinColumn(name = "cardapio_id")
    private CardapioEntity cardapio;
}