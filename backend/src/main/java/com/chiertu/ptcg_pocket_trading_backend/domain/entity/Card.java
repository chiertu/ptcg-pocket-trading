package com.chiertu.ptcg_pocket_trading_backend.domain.entity;

import com.chiertu.ptcg_pocket_trading_backend.domain.value.CardGeneration;
import com.chiertu.ptcg_pocket_trading_backend.domain.value.CardRarity;
import com.chiertu.ptcg_pocket_trading_backend.domain.value.CardRating;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "card_category")
@Table(name = "cards")
public abstract class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "card_code", nullable = false, unique = true)
    private String cardCode;

    @Column(name = "card_name")
    private String cardName;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_rating")
    private CardRating cardRating;

    @ManyToMany
    @JoinTable(
            name = "card_packs_links",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "pack_id")
    )
    private List<CardPack> cardPacks;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_generation")
    private CardGeneration cardGeneration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_illustrator_id")
    private CardIllustrator  cardIllustrator;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_rarity")
    private CardRarity cardRarity;

    @ManyToMany
    @JoinTable(
        name = "card_effects_links",
        joinColumns = @JoinColumn(name = "card_id"),
        inverseJoinColumns = @JoinColumn(name = "effect_id")
    )
    private List<CardEffect> cardEffects;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public CardRating getCardRating() {
        return cardRating;
    }

    public void setCardRating(CardRating cardRating) {
        this.cardRating = cardRating;
    }

    public List<CardPack> getCardPacks() {
        return cardPacks;
    }

    public void setCardPacks(List<CardPack> cardPacks) {
        this.cardPacks = cardPacks;
    }

    public CardGeneration getCardGeneration() {
        return cardGeneration;
    }

    public void setCardGeneration(CardGeneration cardGeneration) {
        this.cardGeneration = cardGeneration;
    }

    public CardIllustrator getCardIllustrator() {
        return cardIllustrator;
    }

    public void setCardIllustrator(CardIllustrator cardIllustrator) {
        this.cardIllustrator = cardIllustrator;
    }

    public CardRarity getCardRarity() {
        return cardRarity;
    }

    public void setCardRarity(CardRarity cardRarity) {
        this.cardRarity = cardRarity;
    }

    public List<CardEffect> getCardEffects() {
        return cardEffects;
    }

    public void setCardEffects(List<CardEffect> cardEffects) {
        this.cardEffects = cardEffects;
    }
}
