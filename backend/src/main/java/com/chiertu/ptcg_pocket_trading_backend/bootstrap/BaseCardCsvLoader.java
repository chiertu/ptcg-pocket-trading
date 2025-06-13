package com.chiertu.ptcg_pocket_trading_backend.bootstrap;

import com.chiertu.ptcg_pocket_trading_backend.domain.valueObject.CardEffect;
import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardIllustrator;
import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardPack;
import com.chiertu.ptcg_pocket_trading_backend.domain.valueObject.CardRarity;
import com.chiertu.ptcg_pocket_trading_backend.domain.valueObject.EffectType;
import com.chiertu.ptcg_pocket_trading_backend.domain.valueObject.TrainerType;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCardCsvLoader {

    protected String parseCardCode(String raw){
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Card code is required.");
        }
        return raw.trim();
    }

    protected String parseCardName(String raw){
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Card name is required.");
        }
        return raw.trim();
    }

    protected Integer parseInteger(String value) {
        try {
            return (value == null || value.isBlank()) ? null : Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            System.err.println("Invalid integer: " + value);
            return null;
        }
    }


    protected List<CardEffect> parseCardEffects(String raw) {
        List<CardEffect> effects = new ArrayList<>();
        if (raw == null || raw.isBlank()) return effects;

        raw = raw.trim();
        if (raw.startsWith("[") && raw.endsWith("]")) {
            raw = raw.substring(1, raw.length() - 1);
        }

        // Split by Effect(...) if there are multiple effects
        String[] rawEffects = raw.split("Effect\\(");
        for (String rawEffect : rawEffects) {
            rawEffect = rawEffect.trim();
            if (rawEffect.isEmpty()) continue;

            // Remove trailing ")"
            if (rawEffect.endsWith(")")) {
                rawEffect = rawEffect.substring(0, rawEffect.length() - 1);
            }

            CardEffect effect = new CardEffect();
            String[] parts = rawEffect.split(",\\s*(?=\\w+=)");

            for (String part : parts) {
                String[] kv = part.split("=", 2);
                if (kv.length != 2) continue;

                String key = kv[0].trim();
                String value = kv[1].trim().replace("'", "");

                switch (key) {
                    case "effect_name" -> effect.setEffectName(value);
                    case "effect_type" -> {
                        try {
                            effect.setEffectType(EffectType.valueOf(value.toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            System.err.println("Invalid effect type: " + value);
                        }
                    }
                    case "effect_description" -> effect.setEffectDescription("None".equalsIgnoreCase(value) ? null : value);
                    case "effect_damage" -> effect.setEffectDamage(parseInteger(value));
                    case "effect_cost" -> effect.setEffectCost(parseEnergyTypes(value));
                }
            }
            effects.add(effect);
        }
        return effects;
    }

    protected CardIllustrator parseCardIllustrator(String raw) {
        return (raw == null || raw.isBlank()) ? null : new CardIllustrator(raw.trim());
    }

    protected CardPack parseCardPack(String raw) {
        return (raw == null || raw.isBlank()) ? null : new CardPack(raw.trim());
    }

    protected TrainerType parseTrainerTypeFromText(String raw) {
        if (raw == null || raw.isBlank()) return null;
        String lower = raw.trim().toLowerCase();
        if (lower.contains("item")) return TrainerType.ITEM;
        if (lower.contains("supporter")) return TrainerType.SUPPORTER;
        if (lower.contains("tool")) return TrainerType.POKEMON_TOOL;
        return TrainerType.OTHER;
    }

    protected CardRarity parseSymbolToRarity(String symbol) {
        if (symbol == null || symbol.isBlank()) return CardRarity.UNKNOWN;
        return switch (symbol.trim()) {
            case "C" -> CardRarity.COMMON;
            case "U" -> CardRarity.UNCOMMON;
            case "R" -> CardRarity.RARE;
            case "RR" -> CardRarity.DOUBLE_RARE;
            default -> CardRarity.UNKNOWN;
        };
    }
}
