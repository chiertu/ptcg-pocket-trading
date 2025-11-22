package com.chiertu.ptcg_pocket_trading_backend.domain.entity;

import com.chiertu.ptcg_pocket_trading_backend.domain.value.*;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("POKEMON")
public class PokemonCard extends Card {

    @Enumerated(EnumType.STRING)
    @Column(name = "card_stage")
    private CardStage cardStage;

    @ElementCollection(targetClass = EnergyType.class)
    @CollectionTable(name = "pokemon_card_types", joinColumns = @JoinColumn(name = "card_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "card_types")
    private Set<EnergyType> cardTypes;

    @ElementCollection(targetClass = EnergyType.class)
    @CollectionTable(name = "pokemon_card_weaknesses", joinColumns = @JoinColumn(name = "card_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "card_weaknesses")
    private Set<EnergyType> cardWeaknesses;

    @Column(name = "card_hp")
    private Integer cardHp;

    @ElementCollection(targetClass = EnergyType.class)
    @CollectionTable(name = "pokemon_card_retreat_costs", joinColumns = @JoinColumn(name = "card_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "card_retreat_costs")
    private List<EnergyType> cardRetreatCosts;

    public CardStage getCardStage() {
        return cardStage;
    }

    public void setCardStage(CardStage cardStage) {
        this.cardStage = cardStage;
    }

    public Set<EnergyType> getCardTypes() {
        return cardTypes;
    }

    public void setCardTypes(Set<EnergyType> cardTypes) {
        this.cardTypes = cardTypes;
    }

    public Set<EnergyType> getCardWeaknesses() {
        return cardWeaknesses;
    }

    public void setCardWeaknesses(Set<EnergyType> cardWeaknesses) {
        this.cardWeaknesses = cardWeaknesses;
    }

    public Integer getCardHp() {
        return cardHp;
    }

    public void setCardHp(Integer cardHp) {
        this.cardHp = cardHp;
    }

    public List<EnergyType> getCardRetreatCosts() {
        return cardRetreatCosts;
    }

    public void setCardRetreatCosts(List<EnergyType> cardRetreatCosts) {
        this.cardRetreatCosts = cardRetreatCosts;
    }
}
