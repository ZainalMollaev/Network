package org.education.network.enumtypes;

public enum Privilege {

    WRITE("write"), READ("read");

    private final String name;

    Privilege(String name) {
        this.name = name;
    }
}
