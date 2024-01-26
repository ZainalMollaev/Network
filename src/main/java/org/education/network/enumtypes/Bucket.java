package org.education.network.enumtypes;

public enum Bucket {

    posts("posts"), users("users");
    private String bucket;
    Bucket(String bucket) {
        this.bucket = bucket;
    }

    public String getBucket(){return bucket;}

}
