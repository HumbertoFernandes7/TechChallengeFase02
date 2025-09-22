package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository;

import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.ItemCardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapioEntity, Long> {
}
