package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity;

import com.fiap.techchallenge.restaurantmanagement.core.domain.Cidade;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Endereco")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoEntity {
    @Id
    private Long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;
    private String bairro;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private CidadeEntity cidade;

}
