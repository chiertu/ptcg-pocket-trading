package com.chiertu.ptcg_pocket_trading_backend.domain.repository;

import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardPack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardPackRepository extends JpaRepository<CardPack, Long> {
    Optional<CardPack> findByPackNameIgnoreCaseAndPackCodeIgnoreCaseAndPackVariationIgnoreCase(
            String packName,
            String packCode,
            String packVariation
    );
}
