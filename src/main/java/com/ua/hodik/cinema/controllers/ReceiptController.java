package com.ua.hodik.cinema.controllers;

import com.ua.hodik.cinema.dto.FilterFormDto;
import com.ua.hodik.cinema.dto.ReceiptDto;
import com.ua.hodik.cinema.model.Session;
import com.ua.hodik.cinema.model.User;
import com.ua.hodik.cinema.services.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/receipt")
public class ReceiptController {
    private final ScheduleService scheduleService;

    public ReceiptController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @GetMapping("/{id}")
    public String receipt(@PathVariable("id") int id, Model model, HttpSession httpSession){
        model.addAttribute("sessionAdminDto", scheduleService.findById(id));
        model.addAttribute("receiptDto", new ReceiptDto());
        return "receipt";
    }


}
