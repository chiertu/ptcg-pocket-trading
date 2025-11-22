package com.chiertu.ptcg_pocket_trading_backend.domain.value;

public enum EnergyType {
    GRASS("grass"),
    FIRE("fire"),
    WATER("water"),
    LIGHTNING("lightning"),
    PSYCHIC("psychic"),
    FIGHTING("fighting"),
    DARKNESS("darkness"),
    METAL("metal"),
    FAIRY("fairy"),
    DRAGON("dragon"),
    COLORLESS("colorless");

    private final String label;

    EnergyType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static EnergyType parse(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }

        String normalized = raw.trim().toLowerCase();

        for (EnergyType t : EnergyType.values()) {
            if (t.label.toLowerCase().equals(normalized)) {
                return t;
            }
        }

        return null;
    }
}
