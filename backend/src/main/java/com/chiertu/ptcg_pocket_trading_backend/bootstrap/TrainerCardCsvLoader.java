package com.chiertu.ptcg_pocket_trading_backend.bootstrap;

import com.chiertu.ptcg_pocket_trading_backend.domain.repository.TrainerCardRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class TrainerCardCsvLoader extends BaseCardCsvLoader{
    private final TrainerCardRepository trainerCardRepository;

    public TrainerCardCsvLoader(TrainerCardRepository repo) {
        this.trainerCardRepository = repo;
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

//                    trainerCardRepository.save(card);

                } catch (Exception e) {
                    System.err.println("Failed to parse row: " + e.getMessage());
                    // optionally log the row or row number
                }
            }
        }
    }
}
