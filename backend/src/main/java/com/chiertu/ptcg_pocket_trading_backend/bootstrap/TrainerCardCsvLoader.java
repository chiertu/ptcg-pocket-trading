package com.chiertu.ptcg_pocket_trading_backend.bootstrap;

import com.chiertu.ptcg_pocket_trading_backend.domain.entity.TrainerCard;
import com.chiertu.ptcg_pocket_trading_backend.domain.repository.TrainerCardRepository;
import com.chiertu.ptcg_pocket_trading_backend.domain.value.TrainerType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class TrainerCardCsvLoader extends BaseCardCsvLoader {

    private final TrainerCardRepository trainerCardRepository;

    public TrainerCardCsvLoader(
            TrainerCardRepository trainerCardRepository,
            CardIllustratorResolver cardIllustratorResolver,
            CardPackResolver cardPackResolver,
            CardEffectResolver cardEffectResolver
    ) {
        super(cardIllustratorResolver, cardPackResolver, cardEffectResolver);
        this.trainerCardRepository = trainerCardRepository;
    }

    public void load(Path csvFilePath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(csvFilePath)) {
            CSVFormat format = CSVFormat.DEFAULT
                    .builder()
                    .setHeader()
                    .get();

            CSVParser parser = format.parse(reader);

            for (CSVRecord record : parser) {
                try {
                    String code = record.get("card_code").trim();

                    // Look for existing card
                    TrainerCard card = trainerCardRepository.findByCardCodeIgnoreCase(code)
                            .orElseGet(TrainerCard::new);

                    card.setCardCode(parseCardCode(record.get("card_code")));
                    card.setCardName(parseCardName(record.get("card_name")));
                    card.setCardRating(parseCardRating(record.get("rating")));
                    card.setCardPacks(parseCardPacks(record.get("pack")));
                    card.setCardGeneration(parseCardGeneration(record.get("generation")));
                    card.setCardIllustrator(parseIllustrator(record.get("illustrator")));

                    card.setTrainerType(parseTrainerType(record.get("type")));

                    card.setCardRarity(parseCardRarity(record.get("rarity")));
                    card.setCardEffects(parseEffects(record.get("effect")));

                    trainerCardRepository.save(card);

                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to persist trainer card record: " + record, e);
                }
            }
        }
    }

    private TrainerType parseTrainerType(String raw) {
        if (raw == null || raw.isBlank()) return null;

        String cleaned = raw.trim();

        // Strip list-like brackets: ['item'] or ["item"]
        if (cleaned.startsWith("[") && cleaned.endsWith("]")) {
            cleaned = cleaned.substring(1, cleaned.length() - 1);
        }

        // Strip quotes
        cleaned = cleaned.replace("'", "")
                .replace("\"", "")
                .trim()
                .toLowerCase();

        if (cleaned.isBlank()) return null;

        switch (cleaned) {
            case "item":
                return TrainerType.ITEM;
            case "supporter":
                return TrainerType.SUPPORTER;
            case "pokemon tool":
            case "pokemon_tool":
            case "pokémon tool":
                return TrainerType.POKEMON_TOOL;
            default:
                // Up to you: return null vs throw.
                // Returning null keeps the loader resilient.
                System.err.println("Unknown trainer type: '" + raw + "', storing null.");
                return null;
        }
    }
}
