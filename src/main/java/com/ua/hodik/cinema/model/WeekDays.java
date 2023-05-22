package com.ua.hodik.cinema.model;

public enum WeekDays {
    SUNDAY("sunday", "НЕДІЛЯ", 1),
    MONDAY("monday", "ПОНЕДІЛОК", 2),
    TUESDAY("tuesday", "ВІВТОРОК", 3),
    WEDNESDAY("wednesday", "СЕРЕДА", 4),
    THURSDAY("thursday", "ЧЕТВЕР", 5),
    FRIDAY("friday", "П'ЯТНИЦЯ", 6),
    SATURDAY("saturday", "СУБОТА", 7);


    final String name;
    final String nameUkr;
    final int number;

    WeekDays(String name, String nameUkr, int number) {
        this.name = name;
        this.nameUkr = nameUkr;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }


    public static WeekDays getByNameIgnoringCase(String name) {
        for (WeekDays value : WeekDays.values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Day not found " + name);
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
