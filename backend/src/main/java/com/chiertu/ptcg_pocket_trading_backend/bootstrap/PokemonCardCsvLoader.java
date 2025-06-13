package com.chiertu.ptcg_pocket_trading_backend.bootstrap;

import com.chiertu.ptcg_pocket_trading_backend.domain.repository.PokemonCardRepository;
import org.springframework.stereotype.Component;

@Component
public class PokemonCardCsvLoader extends BaseCardCsvLoader{
    private final PokemonCardRepository pokemonCardRepository;

    public PokemonCardCsvLoader(PokemonCardRepository repo) {
        this.pokemonCardRepository = repo;
    }
}
