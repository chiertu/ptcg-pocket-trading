package com.chiertu.ptcg_pocket_trading_backend.domain.repository;

import com.chiertu.ptcg_pocket_trading_backend.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CardRepository<T extends Card> extends JpaRepository<T, Long> {
    Optional<T> findByCardCodeIgnoreCase(String cardCode);
}
