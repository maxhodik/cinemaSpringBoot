package com.ua.hodik.cinema.model;

public enum Status {

    ACTIVE("active"), CANCELED("canceled");

    Status(String name) {
        this.name = name;
    }

    String name;

    public static Status getByNameIgnoringCase(String name) {
        for (Status value : Status.values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Status not found " + name);
    }

    public static boolean contains(String name) {
        try {
            getByNameIgnoringCase(name);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

