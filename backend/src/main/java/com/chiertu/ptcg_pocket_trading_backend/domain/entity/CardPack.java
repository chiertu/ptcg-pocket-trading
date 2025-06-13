package com.chiertu.ptcg_pocket_trading_backend.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "card_packs")
public class CardPack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pack_id")
    private Long packId;

    @Column(name = "name", nullable = false)
    private String packName;

    @Column(name = "code", nullable = false)
    private String packCode;

    @Column(name = "variation", nullable = false)
    private String packVariation;

    @Column(name = "release_date")
    private LocalDate packReleaseDate;

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
    public static CardPack parseCardPack(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Pack string is required.");
        }

        String[] parts = raw.trim().split("\\)\\s{2,}", 2); // match ")  "
        if (parts.length != 2 || !parts[0].contains("(")) {
            throw new IllegalArgumentException("Invalid pack format: " + raw);
        }

        int parenIndex = parts[0].lastIndexOf('(');
        String name = parts[0].substring(0, parenIndex).trim();
        String code = parts[0].substring(parenIndex + 1).trim();
        String variation = parts[1].trim();

        return new CardPack(name, code, variation, null); // no release date yet
    }

}
