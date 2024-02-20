package org.education.network.enumtypes;

public enum LikeAction {

    LIKE("like"), DISLIKE("dislike");

    private final String act;

    LikeAction(String act) {
        this.act = act;
    }

    public String getAct() {
        return act;
    }

}

