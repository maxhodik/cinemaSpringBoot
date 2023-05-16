package com.ua.hodik.cinema.controllers;

import com.ua.hodik.cinema.dto.FilterFormDto;
import com.ua.hodik.cinema.dto.SessionAdminDto;
import com.ua.hodik.cinema.model.Session;
import com.ua.hodik.cinema.services.MovieService;
import com.ua.hodik.cinema.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    public final ScheduleService scheduleService;
    public final MovieService movieService;


    @Autowired
    public ScheduleController(ScheduleService scheduleService, MovieService movieService) {
        this.scheduleService = scheduleService;
        this.movieService = movieService;

    }

    @GetMapping()
    public String schedule(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC,  size = 5) Pageable pageable) {
        Page<SessionAdminDto> page = scheduleService.findAll(pageable);
        model.addAttribute("page", page);
        model.addAttribute("movieDto", movieService.findAll(null));
        model.addAttribute("filterFormDto", new FilterFormDto());
        String sort = page.getSort().stream()
                .map(order -> order.getProperty())
                .collect(Collectors.joining(","));
        model.addAttribute("sort", sort);
        return "schedule";
    }

    @PostMapping()
    public String searchSchedule (@ModelAttribute("filterFormDto") FilterFormDto filters,
                                  @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 5) Pageable pageable, Model model) {
        System.out.println(filters);
        // validate searchCriteria
        // scheduleService.search(scheduleService)
        return "redirect:/schedule";
    }



}
