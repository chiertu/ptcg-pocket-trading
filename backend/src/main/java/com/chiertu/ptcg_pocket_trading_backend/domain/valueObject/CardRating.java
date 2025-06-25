package com.chiertu.ptcg_pocket_trading_backend.domain.valueObject;

public enum CardRating {
    UNRATED("-"),
    D("D"),
    C("C"),
    B("B"),
    A("A"),
    A_PLUS("A+"),
    S("S");

    private final String label;
    CardRating(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

    public static CardRating parseCardRating(String raw){
        String normalized = raw.trim().toUpperCase();
        for(CardRating r: CardRating.values()){
            if(r.label.equalsIgnoreCase(normalized)) {
                return r;
            }
        }
        return CardRating.UNRATED;
    }
}
