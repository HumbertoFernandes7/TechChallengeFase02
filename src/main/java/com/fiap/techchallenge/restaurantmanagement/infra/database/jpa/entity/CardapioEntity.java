package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cardapio")
public class CardapioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "restaurante_id")
    private RestauranteEntity restaurante;

    @OneToMany(mappedBy = "cardapio", cascade =  CascadeType.ALL, orphanRemoval = true)
    private List<ItemCardapioEntity> itensCardapio = new ArrayList<>();
}
