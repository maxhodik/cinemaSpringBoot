package com.ua.hodik.cinema.controllers;

import com.ua.hodik.cinema.dto.FilterFormDto;
import com.ua.hodik.cinema.dto.SessionAdminDto;
import com.ua.hodik.cinema.services.MovieService;
import com.ua.hodik.cinema.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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
    public String schedule(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 5) Pageable pageable,
                           @ModelAttribute("filterFormDto") FilterFormDto filterFormDto) {
        filterFormDto.setDateTime(LocalDateTime.now());
//        filterFormDto.setStatus(List.of("ACTIVE"));
        filterFormDto.setAvailableSeats(true);
        Page<SessionAdminDto> sessionPage = scheduleService.findAllWithFilters(filterFormDto, pageable);
        model.addAttribute("page", sessionPage);
        model.addAttribute("movieDto", movieService.findAll(null));
        model.addAttribute("filterFormDto", new FilterFormDto());
        String sort = sessionPage.getSort().stream()
                .map(order -> order.getProperty())
                .collect(Collectors.joining(","));
        model.addAttribute("sort", sort);
        return "schedule";
    }

    @PostMapping()
    public String searchSchedule(@ModelAttribute("filterFormDto") FilterFormDto filterFormDto,
                                 @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 5) Pageable pageable, Model model) {
        // validate searchCriteria
        // scheduleService.search(scheduleService)
        filterFormDto.setDateTime(LocalDateTime.now());
//        filterFormDto.setStatus(List.of("ACTIVE"));
        filterFormDto.setAvailableSeats(true);
        Page<SessionAdminDto> sessionPage = scheduleService.findAllWithFilters(filterFormDto, pageable);
        String sort = sessionPage.getSort().stream()
                .map(order -> order.getProperty())
                .collect(Collectors.joining(","));
        model.addAttribute("filterFormDto", filterFormDto);
        model.addAttribute("page", sessionPage);
        model.addAttribute("movieDto", movieService.findAll(null));
        model.addAttribute("sort", sort);
        return "schedule";
    }

    @PostMapping("/reset")
    public String resetFilers(@ModelAttribute("filterFormDto") FilterFormDto filterFormDto,
                              @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 5) Pageable pageable, Model model) {
        // validate searchCriteria
        // scheduleService.search(scheduleService)
        filterFormDto = new FilterFormDto();
        Page<SessionAdminDto> sessionPage = scheduleService.findAllWithFilters(filterFormDto, pageable);
        String sort = sessionPage.getSort().stream()
                .map(order -> order.getProperty())
                .collect(Collectors.joining(","));
        model.addAttribute("filterFormDto", filterFormDto);
        model.addAttribute("page", sessionPage);
        model.addAttribute("movieDto", movieService.findAll(null));
        model.addAttribute("sort", sort);
        return "schedule";
    }
}
