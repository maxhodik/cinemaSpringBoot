package com.ua.hodik.cinema.util;

import com.ua.hodik.cinema.dto.FilterFormDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FilterFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return this.getClass().equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FilterFormDto filterFormDto= (FilterFormDto) target;

    }
}
