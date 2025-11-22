package com.chiertu.ptcg_pocket_trading_backend.domain.entity;

import com.chiertu.ptcg_pocket_trading_backend.domain.value.TrainerType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("TRAINER")
public class TrainerCard extends Card {

    @Enumerated(EnumType.STRING)
    @Column(name = "trainer_type")
    private TrainerType trainerType;

    @Column(name = "trainer_category")
    private String trainerCategory;

    // ---- Constructors ----

    public TrainerCard() {
        super();
        // If Card has a cardCategory field and setter, you can enforce it here:
        // this.setCardCategory(CardCategory.TRAINER);
    }

    // ---- Getters & Setters ----

    public TrainerType getTrainerType() {
        return trainerType;
    }

    public void setTrainerType(TrainerType trainerType) {
        this.trainerType = trainerType;
    }

    public String getTrainerCategory() {
        return trainerCategory;
    }

    public void setTrainerCategory(String trainerCategory) {
        this.trainerCategory = trainerCategory;
    }
}
