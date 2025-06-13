package com.chiertu.ptcg_pocket_trading_backend.controller;

import com.chiertu.ptcg_pocket_trading_backend.domain.entity.PokemonCard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class TradeRestController {
    @GetMapping("/trades")
    public List<PokemonCard> getTrades(){
        List<PokemonCard> pokemonCardList = new ArrayList<>();
        return pokemonCardList;
    }
    

}
