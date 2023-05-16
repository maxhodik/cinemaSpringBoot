package com.ua.hodik.cinema.controllers;

import com.ua.hodik.cinema.dto.FilterFormDto;
import com.ua.hodik.cinema.dto.SessionAdminDto;
import com.ua.hodik.cinema.services.HallService;
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

import java.util.stream.Collectors;

@Controller
@RequestMapping("/schedule-admin")
public class ScheduleAdminController {
    private final ScheduleService scheduleService;

    private final MovieService movieService;
    private final HallService hallService;


    @Autowired
    public ScheduleAdminController(ScheduleService scheduleService, MovieService movieService, HallService hallService) {
        this.scheduleService = scheduleService;
        this.movieService = movieService;


        this.hallService = hallService;
    }

    @GetMapping()
    public String schedule(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable) {
        Page<SessionAdminDto> page = scheduleService.findAll(pageable);
        System.out.println(page.getSort());
        model.addAttribute("page", page);
        model.addAttribute("movieDto", movieService.findAll(null));
        model.addAttribute("filterFormDto", new FilterFormDto());
        Sort sort1 = page.getSort();
        String sort = sort1.stream()
                .map(Sort.Order::getProperty)
                .collect(Collectors.joining(","));
        model.addAttribute("sort", sort);

        return "admin/schedule-admin";
    }

    @PostMapping()
    public String searchSchedule(@ModelAttribute("filterFormDto") FilterFormDto filters,
                                 @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 5) Pageable pageable, Model model) {
        System.out.println(filters);
        // validate searchCriteria
        // scheduleService.search(scheduleService)
        return "redirect:/admin/schedule-admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("sessionAdminDto", scheduleService.findById(id));
        model.addAttribute("movieDto", movieService.findAll(null));
        return "admin/edit-session";
    }

    @PatchMapping("/edit/{id}")
    public String editSession(@ModelAttribute("sessionAdminDto") SessionAdminDto sessionAdminDto) {
        scheduleService.update(sessionAdminDto);
        return "redirect:/schedule-admin";

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        scheduleService.delete(id);
        return "redirect:/schedule-admin";
    }

    @GetMapping("/add-session")
    public String addSession(@ModelAttribute("sessionAdminDto") SessionAdminDto sessionAdminDto, Model model) {
        model.addAttribute("movieDto", movieService.findAll(null));
        return "admin/add-session";
    }

    @PostMapping("/add-session")
    public String create(@ModelAttribute("sessionAdminDto") SessionAdminDto sessionAdminDto) {
        scheduleService.create(sessionAdminDto);
        return "redirect:/schedule-admin";
    }


}
