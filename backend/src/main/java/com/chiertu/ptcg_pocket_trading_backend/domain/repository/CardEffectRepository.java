package com.chiertu.ptcg_pocket_trading_backend.domain.repository;

import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardEffect;
import com.chiertu.ptcg_pocket_trading_backend.domain.value.EffectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardEffectRepository extends JpaRepository<CardEffect, Long> {

    Optional<CardEffect> findByEffectNameIgnoreCaseAndEffectTypeAndEffectDescriptionIgnoreCase(
            String effectName,
            EffectType effectType,
            String effectDescription
    );
}