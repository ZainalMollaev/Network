package org.education.network.enumtypes;

public enum Roles {

    admin("admin"), user("user");

    private String role;

    Roles(String role) {
        this.role = role;
    }
}
