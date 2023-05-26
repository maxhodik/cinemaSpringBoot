package com.ua.hodik.cinema.util.validation;

import com.ua.hodik.cinema.dto.MovieDto;
import com.ua.hodik.cinema.services.MovieService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class MovieValidator implements Validator {
    private final MovieService movieService;

    public MovieValidator(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return this.getClass().equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MovieDto movieDto = (MovieDto) target;
        MovieDto movie = movieService.findByName(movieDto.getName());
        if (movie != null) {
            errors.rejectValue("name", "", "Movie with this name already exists");
        }


    }
}
