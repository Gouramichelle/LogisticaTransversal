package com.ecomarket.logistica.repository;

import com.ecomarket.logistica.model.LogisticaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticaRepository extends JpaRepository<LogisticaEntity,Integer> {


}
