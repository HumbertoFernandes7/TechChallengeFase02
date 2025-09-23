package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository;

import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository  extends JpaRepository<EnderecoEntity, Long> {
}
