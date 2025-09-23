package com.fiap.techchallenge.restaurantmanagement.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class Cardapio {

   private Long id;

   private Restaurante restaurante;

   private List<ItemCardapio> itensCardapio = new ArrayList<>();

   public Cardapio(Restaurante restaurante) {
       this.restaurante = restaurante;
   }
}