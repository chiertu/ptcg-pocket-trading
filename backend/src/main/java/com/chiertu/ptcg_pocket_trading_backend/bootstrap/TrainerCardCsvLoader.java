package com.chiertu.ptcg_pocket_trading_backend.bootstrap;

import com.chiertu.ptcg_pocket_trading_backend.domain.repository.TrainerCardRepository;
import org.springframework.stereotype.Component;

@Component
public class TrainerCardCsvLoader extends BaseCardCsvLoader{
    private final TrainerCardRepository trainerCardRepository;

    public TrainerCardCsvLoader(TrainerCardRepository repo) {
        this.trainerCardRepository = repo;
    }
}
