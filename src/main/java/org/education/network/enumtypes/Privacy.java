package org.education.network.enumtypes;

public enum Privacy {

    ALL("all"), SUBSCRIBERS("subscribers"), ONLYME("only_me");

    private final String privacy;

    Privacy(String privacy) {
        this.privacy = privacy;
    }
}
