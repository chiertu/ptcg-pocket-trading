package com.chiertu.ptcg_pocket_trading_backend.domain.valueObject;

import com.chiertu.ptcg_pocket_trading_backend.domain.valueObject.EffectType;
import jakarta.persistence.*;

@Embeddable
public class CardEffect {
    @Column(name = "effect_name")
    private String effectName;

    @Column(name = "effect_type")
    private EffectType effectType;

    @Column(name = "effect_description")
    private String effectDescription;

    @Column(name = "effect_cost")
    private Integer effectCost;

    @Column(name = "effect_damage")
    private Integer effectDamage;

}
