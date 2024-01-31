package org.education.network.enumtypes;

public enum Role {

    ADMIN("admin"), USER("user");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}
