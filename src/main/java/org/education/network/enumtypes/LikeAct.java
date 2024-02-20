package org.education.network.enumtypes;

public enum LikeAct {

    PLUS("plus"), MINUS("minus");

    private final String act;

    LikeAct(String act) {
        this.act = act;
    }

    public String getAct() {
        return act;
    }

}
