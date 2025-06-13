package com.chiertu.ptcg_pocket_trading_backend.bootstrap;

import com.chiertu.ptcg_pocket_trading_backend.domain.value.CardEffect;
import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardIllustrator;
import com.chiertu.ptcg_pocket_trading_backend.domain.entity.CardPack;
import com.chiertu.ptcg_pocket_trading_backend.domain.value.CardRarity;
import com.chiertu.ptcg_pocket_trading_backend.domain.value.EffectType;
import com.chiertu.ptcg_pocket_trading_backend.domain.value.TrainerType;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCardCsvLoader {

    protected String parseCardCode(String raw){
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Card code is required.");
        }
        return raw.trim();
    }

    protected String parseCardName(String raw){
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Card name is required.");
        }
        return raw.trim();
    }

    protected CardIllustrator parseCardIllustrator(String raw) {
        return (raw == null || raw.isBlank()) ? null : new CardIllustrator(raw.trim());
    }

}
