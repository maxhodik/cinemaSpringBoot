package com.ua.hodik.cinema.controllers;

import com.ua.hodik.cinema.dto.MovieDto;
import com.ua.hodik.cinema.model.Movie;
import com.ua.hodik.cinema.services.MovieService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;
    private final Validator movieValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieController(MovieService movieService, @Qualifier(value = "movieValidator") Validator movieValidator,
                           ModelMapper modelMapper) {
        this.movieService = movieService;
        this.movieValidator = movieValidator;

        this.modelMapper = modelMapper;
    }


    @GetMapping()
    public String movies(Model model, @RequestParam(value = "offset", required = false) Integer offset,
                         @RequestParam(value = "limit", required = false) Integer limit,
                         @RequestParam(value = "orderBy", required = false) String orderBy) {
        if (offset == null || limit == null) {
            List<Movie> movies = movieService.findAll(orderBy);
            List<MovieDto> movieDtoList = movies.stream().map(x -> convertToMovieDto(x)).toList();
            model.addAttribute("moviesDto", movieService.findAll(orderBy));
        }
        return "movie";
    }

    @GetMapping("/add-movie")
    public String create(@ModelAttribute("movieDto") MovieDto movieDto) {
        return "add-movie";
    }

    @PostMapping("/add-movie")
    public String save(@ModelAttribute("movieDto") @Valid MovieDto movieDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-movie";
        }
        movieValidator.validate(movieDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "add-movie";
        }
        movieService.save(convertToMovie(movieDto));
        return "redirect:/movie";
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable("id") int id) {
        movieService.deleteMovie(id);
        return "redirect:/movie";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Movie movie = movieService.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        model.addAttribute("movieDto", convertToMovieDto(movie));
        return "/edit-movie";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("movieDto")@Valid MovieDto movieDto,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "/edit-movie";
        }
        movieValidator.validate(movieDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/edit-movie";
        }
        movieService.save(convertToMovie(movieDto));
        return "redirect:/movie";
    }



    private Movie convertToMovie(MovieDto movieDto) {
        return modelMapper.map(movieDto, Movie.class);
    }

    private MovieDto convertToMovieDto(Movie movie) {
        return modelMapper.map(movie, MovieDto.class);
    }
}
