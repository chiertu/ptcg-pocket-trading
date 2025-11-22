package com.chiertu.ptcg_pocket_trading_backend.domain.value;

public enum CardStage {
    BASIC("Basic"),
    STAGE_1("Stage 1"),
    STAGE_2("Stage 2");

    private final String label;

    CardStage(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static CardStage parse(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }

        String normalized = raw.trim().toLowerCase();

        for (CardStage stage : CardStage.values()) {
            if (stage.label.toLowerCase().equals(normalized)) {
                return stage;
            }
        }

        return null;
    }
}