package com.chiertu.ptcg_pocket_trading_backend.domain.repository;

import com.chiertu.ptcg_pocket_trading_backend.domain.entity.Card;
import com.chiertu.ptcg_pocket_trading_backend.domain.entity.PokemonCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PokemonCardRepository extends CardRepository<PokemonCard> {}
