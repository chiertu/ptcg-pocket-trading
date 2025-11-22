package com.chiertu.ptcg_pocket_trading_backend.domain.value;

public enum CardRarity {

    DIAMOND_1("◇"),
    DIAMOND_2("◇◇"),
    DIAMOND_3("◇◇◇"),
    DIAMOND_4("◇◇◇◇"),

    STAR_1("☆"),
    STAR_2("☆☆"),
    STAR_3("☆☆☆"),

    CROWN("♛"),

    SHINY_1("✸"),
    SHINY_2("✸✸"),

    PROMO("promo");

    private final String label;

    CardRarity(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    /**
     * Parses rarity like:
     *   ◇, ◇◇◇
     *   ☆☆
     *   ♛
     *   ✸✸
     *   promo
     * Returns null if blank or unknown.
     */
    public static CardRarity parse(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }

        String normalized = raw.trim().toLowerCase();

        for (CardRarity r : CardRarity.values()) {
            if (r.label.toLowerCase().equals(normalized)) {
                return r;
            }
        }

        return null; // unknown rarity treated as empty
    }
}
