package com.amos2020.javabackend.repository;

import com.amos2020.javabackend.entity.FactorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactorRepository extends JpaRepository<FactorEntity, Integer> {
}
