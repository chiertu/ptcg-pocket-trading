package com.chiertu.ptcg_pocket_trading_backend.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "card_illustrators")
public class CardIllustrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "illustrator_id")
    private Long illustratorId;

    @Column(name = "illustrator_name", nullable = false, unique = true)
    private String illustratorName;

    // constructors
    protected CardIllustrator() { }

    public CardIllustrator(String illustratorName) {
        this.illustratorName = illustratorName;
    }

    // getters & setters
    public Long getIllustratorId() {
        return illustratorId;
    }

    public String getIllustratorName() {
        return illustratorName;
    }

    public void setIllustratorName(String illustratorName) {
        this.illustratorName = illustratorName;
    }

}
