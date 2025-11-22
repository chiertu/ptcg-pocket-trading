package com.chiertu.ptcg_pocket_trading_backend.domain.repository;

import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardIllustrator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardIllustratorRepository extends JpaRepository<CardIllustrator, Long> {
    Optional<CardIllustrator> findByIllustratorNameIgnoreCase(String illustratorName);
}
