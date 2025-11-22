package com.chiertu.ptcg_pocket_trading_backend.bootstrap;

import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardEffect;
import com.chiertu.ptcg_pocket_trading_backend.domain.repository.CardEffectRepository;
import com.chiertu.ptcg_pocket_trading_backend.domain.value.EffectType;
import com.chiertu.ptcg_pocket_trading_backend.domain.value.EnergyType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CardEffectResolver {

    private final CardEffectRepository cardEffectRepository;
    private final Map<String, CardEffect> cache = new HashMap<>();

    public CardEffectResolver(CardEffectRepository cardEffectRepository) {
        this.cardEffectRepository = cardEffectRepository;
    }

    public CardEffect resolveEffect(String name,
                                    EffectType type,
                                    String description,
                                    Integer damage,
                                    List<EnergyType> cost) {

        String key = buildKey(name, type, description);

        return cache.computeIfAbsent(key, k ->
                cardEffectRepository
                        .findByEffectNameIgnoreCaseAndEffectTypeAndEffectDescriptionIgnoreCase(
                                normalize(name),
                                type,
                                normalize(description)
                        )
                        .orElseGet(() -> {
                            CardEffect effect = new CardEffect();
                            effect.setEffectName(name);
                            effect.setEffectType(type);
                            effect.setEffectDescription(description);
                            effect.setEffectDamage(damage);
                            effect.setEffectCost(
                                    cost == null ? List.of() : new ArrayList<>(cost)
                            );
                            return cardEffectRepository.save(effect);
                        })
        );
    }

    private String buildKey(String name, EffectType type, String description) {
        return (normalize(name) + "|" +
                (type == null ? "null" : type.name()) + "|" +
                normalize(description)).toLowerCase();
    }

    private String normalize(String s) {
        return s == null ? "" : s.trim();
    }
}