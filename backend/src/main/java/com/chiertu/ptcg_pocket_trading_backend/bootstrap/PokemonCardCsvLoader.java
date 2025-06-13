package com.chiertu.ptcg_pocket_trading_backend.bootstrap;

import com.chiertu.ptcg_pocket_trading_backend.domain.repository.PokemonCardRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class PokemonCardCsvLoader extends BaseCardCsvLoader{
    private final PokemonCardRepository pokemonCardRepository;

    public PokemonCardCsvLoader(PokemonCardRepository repo) {
        this.pokemonCardRepository = repo;
    }

    public void load(Path csvFilePath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(csvFilePath)) {
            CSVFormat format = CSVFormat.DEFAULT
                    .builder()
                    .setHeader()
                    .get();
            CSVParser parser = format.parse(reader);

            for (CSVRecord record : parser) {
                System.out.println(record);
                try {
                    String code = parseCardCode(record.get("card_code"));
                    String name = parseCardName(record.get("card_name"));

//                    pokemonCardRepository.save(card);

                } catch (Exception e) {
                    System.err.println("Failed to parse row: " + e.getMessage());
                    // optionally log the row or row number
                }
            }
        }
    }
}
