package com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.repository;

import com.fiap.techchallenge.restaurantmanagement.infra.database.jpa.entity.CidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository  extends JpaRepository<CidadeEntity, Long> {

}
