package com.ua.hodik.cinema.model;

public enum Role {

   ROLE_ADMIN("Admin"), ROLE_USER("User");


    Role(String name) {
        this.name = name;
    }

    String name;

    public static Role getByNameIgnoringCase(String name) {
        for (Role value : Role.values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Role not found " + name);
    }
}
