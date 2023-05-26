package com.ua.hodik.cinema.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class CustomTimeValidator implements ConstraintValidator<ValidTime, LocalTime> {
    private final LocalTime START_TIME = LocalTime.of(9,00);
    private final LocalTime END_TIME = LocalTime.of(22,00);


    @Override
    public boolean isValid(LocalTime localTime, ConstraintValidatorContext constraintValidatorContext) {
        return (localTime!=null && localTime.isAfter(START_TIME.minusNanos(1)) && localTime.isBefore(END_TIME.plusNanos(1)));
    }
}
