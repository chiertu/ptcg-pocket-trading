package com.chiertu.ptcg_pocket_trading_backend.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Objects;

@Component
public class CsvLoaderRunner implements CommandLineRunner {
    private final PokemonCardCsvLoader pokemonCardCsvLoader;
    private final TrainerCardCsvLoader trainerCardCsvLoader;
    public CsvLoaderRunner(PokemonCardCsvLoader pokemonCardCsvLoader,
                           TrainerCardCsvLoader trainerCardCsvLoader){
        this.pokemonCardCsvLoader = pokemonCardCsvLoader;
        this.trainerCardCsvLoader = trainerCardCsvLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        Path pokemonCsvPath = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("data/card_details_pokemon.csv")).toURI());
        Path trainerCsvPath = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("data/card_details_trainer.csv")).toURI());
        pokemonCardCsvLoader.load(pokemonCsvPath);
        trainerCardCsvLoader.load(trainerCsvPath);
    }
}
