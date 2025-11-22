package com.chiertu.ptcg_pocket_trading_backend.domain.entity;

import com.chiertu.ptcg_pocket_trading_backend.domain.value.EffectType;
import com.chiertu.ptcg_pocket_trading_backend.domain.value.EnergyType;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.util.List;

@Entity
@Table(name = "card_effects")
public class CardEffect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "effect_id")
    private Long effectId;
    @Column(name = "effect_name", nullable = false)
    private String effectName;

    @Column(name = "effect_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EffectType effectType;


    @Column(name = "effect_description", length = 1024)
    private String effectDescription;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "card_effect_costs",
            joinColumns = @JoinColumn(name = "effect_id")
    )
    @Column(name = "effect_cost")
    @Enumerated(EnumType.STRING)
    private List<EnergyType> effectCost;

    @Column(name = "effect_damage")
    private Integer effectDamage;

    public String getEffectName() {
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public void setEffectType(EffectType effectType) {
        this.effectType = effectType;
    }

    public String getEffectDescription() {
        return effectDescription;
    }

    public void setEffectDescription(String effectDescription) {
        this.effectDescription = effectDescription;
    }

    public List<EnergyType> getEffectCost() {
        return effectCost;
    }

    public void setEffectCost(List<EnergyType> effectCost) {
        this.effectCost = effectCost;
    }

    public Integer getEffectDamage() {
        return effectDamage;
    }

    public void setEffectDamage(Integer effectDamage) {
        this.effectDamage = effectDamage;
    }
}
