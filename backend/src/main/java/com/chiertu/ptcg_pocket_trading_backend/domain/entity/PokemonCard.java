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

}
