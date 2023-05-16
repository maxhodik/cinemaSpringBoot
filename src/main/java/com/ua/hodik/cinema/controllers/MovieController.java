package com.ua.hodik.cinema.controllers;

import com.ua.hodik.cinema.dto.MovieDto;
import com.ua.hodik.cinema.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;
    private final Validator movieValidator;


    @Autowired
    public MovieController(MovieService movieService, @Qualifier(value = "movieValidator") Validator movieValidator) {
        this.movieService = movieService;
        this.movieValidator = movieValidator;


    }

    //
    @GetMapping()
    public String movies(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 5) Pageable pageable) {
        Page<MovieDto> page = movieService.findAllPageable(pageable);
        model.addAttribute("page", page);
        String sort= page.getSort().stream().map(x->x.getProperty()).collect(Collectors.joining(","));
        model.addAttribute("sort", sort);
        return "admin/movie";
    }



    @GetMapping("/add-movie")
    public String create(@ModelAttribute("movieDto") MovieDto movieDto) {
        return "admin/add-movie";
    }

    @PostMapping("/add-movie")
    public String save(@ModelAttribute("movieDto") @Valid MovieDto movieDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/add-movie";
        }
        movieValidator.validate(movieDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/add-movie";
        }
        movieService.save(movieDto);
        return "redirect:/movie";
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable("id") int id) {
        movieService.deleteMovie(id);
        return "redirect:/movie";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("movieDto", movieService.findById(id));
        return "admin/edit-movie";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("movieDto") @Valid MovieDto movieDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit-movie";
        }
        movieValidator.validate(movieDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/edit-movie";
        }
        movieService.save(movieDto);
        return "redirect:/movie";
    }


}
