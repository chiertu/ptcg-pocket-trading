package com.chiertu.ptcg_pocket_trading_backend.domain.value;


public enum CardGeneration {
    GEN_I("Gen 1"),
    GEN_II("Gen 2"),
    GEN_III("Gen 3"),
    GEN_IV("Gen 4"),
    GEN_V("Gen 5"),
    GEN_VI("Gen 6"),
    GEN_VII("Gen 7"),
    GEN_VIII("Gen 8"),
    GEN_IX("Gen 9");

    private final String label;

    CardGeneration(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static CardGeneration parse(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }

        String normalized = raw.trim().toUpperCase();

        for (CardGeneration g : CardGeneration.values()) {
            if (g.label.toUpperCase().equals(normalized)) {
                return g;
            }
        }

        return null;
    }
}
