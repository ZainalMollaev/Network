package org.education.network.enumtypes;

public enum Privileges {

    write("write"), read("read");

    private String name;

    Privileges(String name) {
        this.name = name;
    }
}
