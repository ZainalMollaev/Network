package org.education.network.enumtypes;

import lombok.Getter;

@Getter
public enum Bucket {

    POSTS("post"), AVATAR("avatar"), BACKGROUND("background");

    private String bucket;

    Bucket(String bucket) {
        this.bucket = bucket;
    }

    @Override
    public String toString() {
        return bucket;
    }

}
