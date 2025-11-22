package com.chiertu.ptcg_pocket_trading_backend.bootstrap;


import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardIllustrator;
import com.chiertu.ptcg_pocket_trading_backend.domain.repository.CardIllustratorRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CardIllustratorResolver {

    private final CardIllustratorRepository illustratorRepository;
    private final Map<String, CardIllustrator> cache = new HashMap<>();

    public CardIllustratorResolver(CardIllustratorRepository illustratorRepository) {
        this.illustratorRepository = illustratorRepository;
    }

    public CardIllustrator resolveIllustrator(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Illustrator name must not be null");
        }

        String normalized = name.trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Illustrator name must not be blank");
        }

        String key = normalized.toLowerCase();

        return cache.computeIfAbsent(key, k ->
                illustratorRepository.findByIllustratorNameIgnoreCase(normalized)
                        .orElseGet(() -> illustratorRepository.save(new CardIllustrator(normalized)))
        );
    }
}