package com.chiertu.ptcg_pocket_trading_backend.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(
        name = "card_packs",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "code", "variation"})
)
public class CardPack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pack_id")
    private Long packId;

    @Column(name = "pack_name", nullable = false)
    private String packName;

    @Column(name = "pack_code", nullable = false)
    private String packCode;

    @Column(name = "pack_variation", nullable = false)
    private String packVariation;

    @Column(name = "pack_release_date")
    private LocalDate packReleaseDate;

    private static final Map<String, List<String>> VARIATIONS_BY_CODE = Map.of(
            "A1", List.of("Any", "Charizard", "Mewtwo", "Pikachu", "Special"),
            "A2", List.of("Any (Space-Time Smackdown)", "Dialga", "Palkia"),
            "A3", List.of("Any (Celestial Guardians)", "Lunala", "Solgaleo")
            // extend this as you discover more
    );

    // Default constructor
    public CardPack() {}

    // Constructor with all fields except ID (auto-generated)
    public CardPack(String packName, String packCode, String packVariation, LocalDate packReleaseDate) {
        this.packName = packName;
        this.packCode = packCode;
        this.packVariation = packVariation;
        this.packReleaseDate = packReleaseDate;
    }

    // Static parse method
    public static CardPack parse(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Pack string is required.");
        }

        String trimmed = raw.trim();

        // -----------------------------------------
        // 1. SPECIAL CASE: "Promo  Promo-A"
        // -----------------------------------------
        if (trimmed.startsWith("Promo")) {
            String[] promoParts = trimmed.split("\\s{2,}", 2);  // split on double spaces
            if (promoParts.length == 2) {
                String packName = promoParts[0].trim();      // "Promo"
                String variation = promoParts[1].trim();     // "Promo-A"
                String packCode = "PROMO";                   // define a code for promo packs

                return new CardPack(packName, packCode, variation, null);
            } else {
                throw new IllegalArgumentException("Invalid Promo format: " + raw);
            }
        }

        // -----------------------------------------
        // 2. NORMAL CASE: "Genetic Apex (A1)  Mewtwo"
        // -----------------------------------------
        String[] parts = trimmed.split("\\)\\s{2,}", 2); // split on ")  "
        if (parts.length != 2 || !parts[0].contains("(")) {
            throw new IllegalArgumentException("Invalid pack format: " + raw);
        }

        String nameAndCode = parts[0] + ")";   // add back the ')' we split on
        String variation = parts[1].trim();

        int parenIndex = nameAndCode.lastIndexOf('(');
        String packName = nameAndCode.substring(0, parenIndex).trim();
        String packCode = nameAndCode.substring(parenIndex + 1, nameAndCode.length() - 1).trim();

        return new CardPack(packName, packCode, variation, null);
    }

    public static List<CardPack> parsePacks(String raw) {
        CardPack base = parse(raw);

        String variation = base.getPackVariation();
        String code      = base.getPackCode();

        // If it's not an "Any..." row, just return a single-pack list
        if (variation == null || !variation.startsWith("Any")) {
            return List.of(base);
        }

        // It's an "Any..." variant: expand to all packs in that batch (same code)
        List<String> allVariations = VARIATIONS_BY_CODE.get(code);

        if (allVariations == null || allVariations.isEmpty()) {
            // Fallback: if we don't know the batch, just keep the "Any" pack
            return List.of(base);
        }

        // Build a CardPack for each variation in that batch
        List<CardPack> result = new ArrayList<>();
        for (String v : allVariations) {
            result.add(new CardPack(
                    base.getPackName(),
                    base.getPackCode(),
                    v,
                    base.getPackReleaseDate()
            ));
        }
        return result;
    }

    public Long getPackId() {
        return packId;
    }

    public void setPackId(Long packId) {
        this.packId = packId;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public String getPackVariation() {
        return packVariation;
    }

    public void setPackVariation(String packVariation) {
        this.packVariation = packVariation;
    }

    public LocalDate getPackReleaseDate() {
        return packReleaseDate;
    }

    public void setPackReleaseDate(LocalDate packReleaseDate) {
        this.packReleaseDate = packReleaseDate;
    }
}
