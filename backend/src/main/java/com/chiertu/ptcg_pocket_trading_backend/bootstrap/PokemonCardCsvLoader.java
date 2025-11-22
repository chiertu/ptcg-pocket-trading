package com.chiertu.ptcg_pocket_trading_backend.bootstrap;

import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardIllustrator;
import com.chiertu.ptcg_pocket_trading_backend.domain.entity.PokemonCard;
import com.chiertu.ptcg_pocket_trading_backend.domain.repository.CardIllustratorRepository;
import com.chiertu.ptcg_pocket_trading_backend.domain.repository.PokemonCardRepository;
import com.chiertu.ptcg_pocket_trading_backend.domain.value.CardStage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Component
public class PokemonCardCsvLoader extends BaseCardCsvLoader{
    private final PokemonCardRepository pokemonCardRepository;

    public PokemonCardCsvLoader(
            PokemonCardRepository pokemonCardRepo,
            CardIllustratorResolver cardIllustratorResolver,
            CardPackResolver cardPackResolver,
            CardEffectResolver cardEffectResolver
    ){
        super(cardIllustratorResolver, cardPackResolver, cardEffectResolver);
        this.pokemonCardRepository = pokemonCardRepo;
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
                    PokemonCard card = pokemonCardRepository.findByCardCodeIgnoreCase(code)
                            .orElseGet(PokemonCard::new);


                    card.setCardCode(parseCardCode(record.get("card_code")));
                    card.setCardName(parseCardName(record.get("card_name")));
                    card.setCardRating(parseCardRating(record.get("rating")));
                    card.setCardPacks(parseCardPacks(record.get("pack")));
                    card.setCardGeneration(parseCardGeneration(record.get("generation")));
                    card.setCardIllustrator(parseIllustrator(record.get("illustrator")));
                    card.setCardTypes(parseEnergySet(record.get("type")));
                    card.setCardStage(parseCardStage(record.get("stage")));
                    card.setCardWeaknesses(parseEnergySet(record.get("weakness")));
                    card.setCardHp(parseInt(record.get("hp")));
                    card.setCardRetreatCosts(parseEnergyList(record.get("retreat_cost")));
                    card.setCardRarity(parseCardRarity(record.get("rarity")));
                    card.setCardEffects(parseEffects(record.get("effect")));

                    pokemonCardRepository.save(card);

                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to persist: ", e);
                }
            }
        }
    }

    private CardStage parseCardStage(String raw){return CardStage.parse(raw.trim());}

}
