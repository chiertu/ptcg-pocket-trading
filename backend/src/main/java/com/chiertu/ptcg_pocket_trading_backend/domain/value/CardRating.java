package com.chiertu.ptcg_pocket_trading_backend.domain.value;

public enum CardRating {
    D("D"),
    C("C"),
    B("B"),
    A("A"),
    A_PLUS("A+"),
    S("S"),
    S_DOUBLE("SS");

    private final String label;
    CardRating(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

    public static CardRating parse(String raw){
        String normalized = raw.trim().toUpperCase();
        for(CardRating r: CardRating.values()){
            if(r.label.equalsIgnoreCase(normalized)) {
                return r;
            }
        }
        return null;
    }
}
