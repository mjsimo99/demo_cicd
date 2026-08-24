package com.satoru.demo_cicd.repository;

import com.satoru.demo_cicd.model.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, Long> {
    Optional<Deal> findByDealUniqueId(String dealUniqueId);

    boolean existsByDealUniqueId(String dealUniqueId);
}

