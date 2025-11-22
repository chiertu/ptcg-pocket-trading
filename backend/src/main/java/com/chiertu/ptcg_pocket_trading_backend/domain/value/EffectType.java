package com.chiertu.ptcg_pocket_trading_backend.domain.value;

public enum EffectType {
    ABILITY,
    ATTACK,
    TRAINER_EFFECT;
    public static EffectType parse(String raw) {
        if (raw == null || raw.isBlank()) {
            return null; // treat as empty field
        }

        String normalized = raw.trim().replace(" ", "_").toUpperCase();

        for (EffectType type : EffectType.values()) {
            if (type.name().equalsIgnoreCase(normalized)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown EffectType: " + raw);
    }
}
