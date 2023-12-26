package org.education.network;

public enum MediaTypes {

    AVATAR("avatar"),
    BACK("back"),
    POST("post");

    private String type;

    MediaTypes(String type) {
        this.type = type;
    }
}
