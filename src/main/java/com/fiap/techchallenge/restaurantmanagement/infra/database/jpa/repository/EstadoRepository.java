package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository;

import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.EstadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoRepository  extends JpaRepository<EstadoEntity, Long> {
}
