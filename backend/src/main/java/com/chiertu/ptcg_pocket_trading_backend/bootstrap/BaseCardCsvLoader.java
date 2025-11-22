package com.chiertu.ptcg_pocket_trading_backend.bootstrap;

import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardEffect;
import com.chiertu.ptcg_pocket_trading_backend.domain.value.*;
import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardIllustrator;
import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardPack;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class BaseCardCsvLoader {

    protected final CardIllustratorResolver cardIllustratorResolver;
    protected final CardPackResolver cardPackResolver;
    protected final CardEffectResolver cardEffectResolver;
    protected  BaseCardCsvLoader(
            CardIllustratorResolver cardIllustratorResolver,
            CardPackResolver cardPackResolver,
            CardEffectResolver cardEffectResolver
    ){
        this.cardIllustratorResolver = cardIllustratorResolver;
        this.cardPackResolver = cardPackResolver;
        this.cardEffectResolver = cardEffectResolver;
    }

    protected String parseRequiredString(String raw, String fieldName) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        return raw.trim();
    }

    protected String parseCardCode(String raw) {
        return parseRequiredString(raw, "Card code");
    }

    protected String parseCardName(String raw) {
        return parseRequiredString(raw, "Card name");
    }

    protected CardRating parseCardRating(String raw) {
        return CardRating.parse(raw);
    }

    protected List<CardPack> parseCardPacks(String raw){
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Pack string is required.");
        }

        List<CardPack> parsedPacks = CardPack.parsePacks(raw);

        List<CardPack> resolvedCardPacks = new ArrayList<>();
        for (CardPack p : parsedPacks) {
            resolvedCardPacks.add(cardPackResolver.resolveCardPack(p.getPackName(), p.getPackCode(), p.getPackVariation()));
        }
        return resolvedCardPacks;
    }

    protected CardGeneration parseCardGeneration(String raw){return CardGeneration.parse(raw.trim());}
    protected CardRarity parseCardRarity(String raw){return CardRarity.parse(raw.trim());}

    protected Integer parseInt(String raw) {
        if (raw == null || raw.isBlank()) return null;

        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            return null; // treat invalid as empty
        }
    }

    protected List<EnergyType> parseEnergyList(String raw){
        if (raw == null || raw.isBlank()) return null;

        String[] parts = raw.split("[,;]"); // supports both comma and semicolon
        List<EnergyType> list = new ArrayList<>();

        for (String part : parts) {
            EnergyType type = EnergyType.parse(part);
            if (type != null) {
                list.add(type);
            }
        }

        return list.isEmpty() ? null : list;
    }

    protected Set<EnergyType> parseEnergySet(String raw){
        if (raw == null || raw.isBlank()) return null;

        String[] parts = raw.split("[,;]");
        Set<EnergyType> set = new LinkedHashSet<>(); // preserves original CSV order

        for (String part : parts) {
            EnergyType type = EnergyType.parse(part);
            if (type != null) {
                set.add(type);
            }
        }

        return set.isEmpty() ? null : set;
    }

    protected CardIllustrator parseIllustrator(String raw) {
        if (raw == null) return null;

        String name = raw.trim();
        if (name.isEmpty() || "-".equals(name)) {
            return null;
        }

        // Now delegate to resolver with a clean name
        return cardIllustratorResolver.resolveIllustrator(name);
    }

    protected List<CardEffect> parseEffects(String raw) {
        if (raw == null || raw.isBlank()) return null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(raw);

            if (!root.isArray()) {
                throw new IllegalArgumentException("Effects JSON must be an array: " + raw);
            }

            List<CardEffect> effects = new ArrayList<>();

            for (JsonNode node : root) {

                // 1) Name
                String name = getTextOrNull(node, "effect_name");

                // 2) Type
                EffectType type = null;
                String typeRaw = getTextOrNull(node, "effect_type");
                if (typeRaw != null && !typeRaw.isBlank()) {
                    type = EffectType.parse(typeRaw);  // your existing parse
                }

                // 3) Description
                String description = getTextOrNull(node, "effect_description");

                // 4) Cost -> List<EnergyType>
                List<EnergyType> cost = null;
                JsonNode costNode = node.get("effect_cost");
                if (costNode != null && costNode.isArray()) {
                    List<EnergyType> parsedCost = new ArrayList<>();

                    for (JsonNode c : costNode) {
                        String costRaw = c.asText();
                        if (costRaw == null) continue;

                        costRaw = costRaw.trim();
                        if (costRaw.isEmpty()) continue;

                        String base = costRaw;
                        int count = 1;

                        String[] parts = costRaw.split("\\s+");
                        if (parts.length > 1) {
                            String last = parts[parts.length - 1];
                            try {
                                int parsed = Integer.parseInt(last);
                                if (parsed > 0) {
                                    count = parsed;
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 0; i < parts.length - 1; i++) {
                                        if (i > 0) sb.append(' ');
                                        sb.append(parts[i]);
                                    }
                                    base = sb.toString();
                                }
                            } catch (NumberFormatException ignored) {
                                // no numeric suffix
                            }
                        }

                        EnergyType energyType = EnergyType.parse(base);
                        if (energyType != null) {
                            for (int i = 0; i < count; i++) {
                                parsedCost.add(energyType);
                            }
                        }
                    }

                    cost = parsedCost.isEmpty() ? null : parsedCost;
                }

                // 5) Damage
                Integer damage = null;
                JsonNode damageNode = node.get("effect_damage");
                if (damageNode != null && !damageNode.isNull()) {
                    damage = damageNode.asInt();
                }

                // 6) Use resolver -> persisted CardEffect
                CardEffect effect = cardEffectResolver.resolveEffect(
                        name,
                        type,
                        description,
                        damage,
                        cost
                );

                effects.add(effect);
            }

            return effects;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid effect JSON: " + raw, e);
        }
    }

    /**
     * Small helper: safely get text or null.
     */
    private String getTextOrNull(JsonNode node, String fieldName) {
        JsonNode f = node.get(fieldName);
        if (f == null || f.isNull()) return null;
        return f.asText();
    }


}
