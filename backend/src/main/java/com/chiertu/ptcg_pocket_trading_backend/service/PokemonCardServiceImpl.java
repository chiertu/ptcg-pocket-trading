package com.chiertu.ptcg_pocket_trading_backend.service;

import com.chiertu.ptcg_pocket_trading_backend.domain.repository.PokemonCardRepository;
import org.springframework.stereotype.Service;

@Service
public class PokemonCardServiceImpl implements PokemonCardService{
    private final PokemonCardRepository pokemonCardRepository;


    public PokemonCardServiceImpl(PokemonCardRepository pokemonCardRepository) {
        this.pokemonCardRepository = pokemonCardRepository;
    }
}
