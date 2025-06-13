package com.chiertu.ptcg_pocket_trading_backend.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "card_illustrators")
public class CardIllustrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "illustrator_id")
    private Long illustratorId;

    @Column(name = "illustrator_name")
    private String illustratorName;

    public CardIllustrator(String illustratorName) {
        this.illustratorName = illustratorName;
    }

    public CardIllustrator() {

    }

}
