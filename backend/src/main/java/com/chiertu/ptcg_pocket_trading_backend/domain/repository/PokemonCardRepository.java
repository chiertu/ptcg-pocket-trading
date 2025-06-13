package com.chiertu.ptcg_pocket_trading_backend.domain.repository;

import com.chiertu.ptcg_pocket_trading_backend.domain.entity.PokemonCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonCardRepository extends JpaRepository<PokemonCard, Long> {
}
