package com.ua.hodik.cinema.util.validation;

import com.ua.hodik.cinema.dto.ReceiptDto;
import com.ua.hodik.cinema.model.Receipt;
import com.ua.hodik.cinema.services.ScheduleService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReceiptValidator implements Validator {
    private final ScheduleService scheduleService;

    public ReceiptValidator(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Receipt.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReceiptDto receipt = (ReceiptDto) target;
        int sessionId = receipt.getSessionId();
        if (scheduleService.findById(sessionId) == null) {
            errors.rejectValue("session", "", "Wrong session");
        }
    }
}
