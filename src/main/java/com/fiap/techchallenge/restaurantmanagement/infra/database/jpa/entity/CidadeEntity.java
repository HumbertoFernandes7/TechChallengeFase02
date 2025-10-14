package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Cidade")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CidadeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoEntity estado;

}
