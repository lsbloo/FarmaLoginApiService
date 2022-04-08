package com.poc.FarmaLoginService.model;

public enum TypeRoles {


    ROLE_USER("ROLE_USER"),
    ROLE_MODERATOR("ROLE_MODERATOR"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String text;

    TypeRoles(final String role_user) {
        this.text = role_user;
    }
}
