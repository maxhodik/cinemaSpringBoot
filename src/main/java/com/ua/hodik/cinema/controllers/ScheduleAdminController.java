package com.ua.hodik.cinema.controllers;

import com.ua.hodik.cinema.dto.FilterFormDto;
import com.ua.hodik.cinema.dto.SessionAdminDto;
import com.ua.hodik.cinema.model.Status;
import com.ua.hodik.cinema.services.HallService;
import com.ua.hodik.cinema.services.MovieService;
import com.ua.hodik.cinema.services.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/schedule-admin")
public class ScheduleAdminController {
    private final ScheduleService scheduleService;
    private final MovieService movieService;
    private final HallService hallService;
    private final Validator filterFormValidator;



    @Autowired
    public ScheduleAdminController(ScheduleService scheduleService, MovieService movieService, HallService hallService,
                                   @Qualifier("filterFormValidator") Validator filterFormValidator) {
        this.scheduleService = scheduleService;
        this.movieService = movieService;


        this.hallService = hallService;
        this.filterFormValidator = filterFormValidator;

    }

    @GetMapping()
    public String schedule(Model model,
                           @RequestParam(value = "sort", defaultValue = "id,ASC") String sort,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "5") int size) {
        String[] sortProperties = sort.split(",");
        String sortBy;
        Sort.Direction sortDirection = Sort.Direction.DESC;
        if (sortProperties.length >= 2) {
            sortBy = sortProperties[0];
            sortDirection = Sort.Direction.fromString(sortProperties[1]);
        } else {
            sortBy = sortProperties[0];
        }
        FilterFormDto filterFormDto = (FilterFormDto) model.getAttribute("filterFormDto");

        if (filterFormDto == null) {
            filterFormDto = new FilterFormDto();
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<SessionAdminDto> sessionPage = scheduleService.findAllWithFilters(filterFormDto, pageable);

        model.addAttribute("filterFormDto", filterFormDto);
        model.addAttribute("page", sessionPage);
        model.addAttribute("movieDto", movieService.findAll(null));
        model.addAttribute("sort", sort);

        return "admin/schedule-admin";

    }

    @PostMapping()
    public String applyFilters(@ModelAttribute("filterFormDto") @Valid FilterFormDto filterFormDto, BindingResult bindingResult,
                               Model model,
                               @RequestParam(value = "sort", defaultValue = "id,DESC") String sort,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "5") int size) {
        String[] sortProperties = sort.split(",");
        String sortBy;
        Sort.Direction sortDirection = Sort.Direction.DESC;
        if (sortProperties.length >= 2) {
            sortBy = sortProperties[0];
            sortDirection = Sort.Direction.fromString(sortProperties[1]);
        } else {
            sortBy = sortProperties[0];
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        filterFormValidator.validate(filterFormDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/schedule-admin";
        }
        Page<SessionAdminDto> sessionPage = scheduleService.findAllWithFilters(filterFormDto, pageable);

        model.addAttribute("filterFormDto", filterFormDto);
        model.addAttribute("page", sessionPage);
        model.addAttribute("movieDto", movieService.findAll(null));
        model.addAttribute("sort", sort);

        return "admin/schedule-admin";
    }

    @PostMapping("/reset")
    public String resetFilters(@ModelAttribute("filterFormDto") FilterFormDto filterFormDto, Model model,
                               @RequestParam(value = "sort", defaultValue = "id,DESC") String sort,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "5") int size) {
        filterFormDto = new FilterFormDto();
        String[] sortProperties = sort.split(",");
        String sortBy;
        Sort.Direction sortDirection = Sort.Direction.DESC;
        if (sortProperties.length >= 2) {
            sortBy = sortProperties[0];
            sortDirection = Sort.Direction.fromString(sortProperties[1]);
        } else {
            sortBy = sortProperties[0];
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<SessionAdminDto> sessionPage = scheduleService.findAllWithFilters(filterFormDto, pageable);

        model.addAttribute("filterFormDto", filterFormDto);
        model.addAttribute("page", sessionPage);
        model.addAttribute("movieDto", movieService.findAll(null));
        model.addAttribute("sort", sort);
        return "admin/schedule-admin";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        SessionAdminDto sessionDto = scheduleService.findById(id);
        if (sessionDto.getNumberOfSoldSeats()>0 || sessionDto.getStatus() == Status.CANCELED) {
            redirectAttributes.addFlashAttribute("editError", true);
            return "redirect:/admin/schedule-admin";
        }

        model.addAttribute("sessionAdminDto", sessionDto);
        model.addAttribute("movieDto", movieService.findAll(null));
        return "admin/edit-session";
    }

    @RequestMapping("/edit-session")
    public String editSession(@ModelAttribute("sessionAdminDto") @Valid SessionAdminDto sessionAdminDto, BindingResult bindingResult, Model model) {
        int id = sessionAdminDto.getId();
        if (bindingResult.hasErrors()) {
            model.addAttribute("movieDto", movieService.findAll(null));
            return "admin/edit-session";
        }
        scheduleService.update(sessionAdminDto);
        return "redirect:/admin/schedule-admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        scheduleService.delete(id);
        return "redirect:/admin/schedule-admin";
    }

    @GetMapping("/add-session")
    public String addSession(@ModelAttribute("sessionAdminDto") SessionAdminDto sessionAdminDto, Model model) {
        model.addAttribute("movieDto", movieService.findAll(null));
        return "admin/add-session";
    }

    @PostMapping("/add-session")
    public String create(@ModelAttribute("sessionAdminDto") @Valid SessionAdminDto sessionAdminDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("movieDto", movieService.findAll(null));
            return "admin/add-session";
        }
        scheduleService.create(sessionAdminDto);
        return "redirect:/admin/schedule-admin";
    }


}
