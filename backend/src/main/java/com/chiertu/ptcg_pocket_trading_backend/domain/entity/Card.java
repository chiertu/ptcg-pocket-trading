package com.chiertu.ptcg_pocket_trading_backend.domain.entity;

import com.chiertu.ptcg_pocket_trading_backend.domain.value.CardEffect;
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

    @Column(name = "card_code")
    private String cardCode;

    @Column(name = "card_name")
    private String cardName;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_rating")
    private CardRating cardRating;

    @ManyToMany
    @JoinTable(name = "card_pack_links", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "card_packs")
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

    @ElementCollection
    @CollectionTable(name = "card_effects_links", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "card_effects")
    private List<CardEffect> cardEffects;

}
