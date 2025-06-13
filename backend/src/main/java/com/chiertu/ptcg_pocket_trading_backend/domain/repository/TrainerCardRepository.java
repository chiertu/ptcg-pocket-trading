package com.chiertu.ptcg_pocket_trading_backend.domain.repository;

import com.chiertu.ptcg_pocket_trading_backend.domain.entity.TrainerCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerCardRepository extends JpaRepository<TrainerCard, Long> {
}
