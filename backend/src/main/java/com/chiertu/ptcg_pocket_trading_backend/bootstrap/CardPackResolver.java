package com.chiertu.ptcg_pocket_trading_backend.bootstrap;

import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardPack;
import com.chiertu.ptcg_pocket_trading_backend.domain.repository.CardPackRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CardPackResolver {

    private final CardPackRepository cardPackRepository;
    private final Map<String, CardPack> cache = new HashMap<>();

    public CardPackResolver(CardPackRepository cardPackRepository) {
        this.cardPackRepository = cardPackRepository;
    }

    public CardPack resolveCardPack(String packName, String packCode, String packVariation) {
        String key = (packName + "|" + packCode + "|" + packVariation).toLowerCase();

        return cache.computeIfAbsent(key, k ->
                cardPackRepository
                        .findByPackNameIgnoreCaseAndPackCodeIgnoreCaseAndPackVariationIgnoreCase(
                                packName, packCode, packVariation
                        )
                        .orElseGet(() -> cardPackRepository.save(
                                new CardPack(packName, packCode, packVariation, null)
                        ))
        );
    }
}

