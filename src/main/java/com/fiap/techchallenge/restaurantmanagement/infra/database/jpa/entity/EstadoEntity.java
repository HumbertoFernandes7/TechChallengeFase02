package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Estado")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sigla;
}
