package org.education.network.enumtypes;

public enum Privacies {

    ALL("all"), SUBSCRIBERS("subscribers"), ONLYME("only_me");

    private final String privacy;

    Privacies(String privacy) {
        this.privacy = privacy;
    }
}
