package org.education.network.enumtypes;

public enum Bucket {

    POSTS("posts"), USERS("users");
    private String bucket;
    Bucket(String bucket) {
        this.bucket = bucket;
    }

    public String getBucket(){return bucket;}

}
