package com.ua.hodik.cinema.util.validation;

import com.ua.hodik.cinema.dto.FilterFormDto;
import com.ua.hodik.cinema.dto.MovieDto;
import com.ua.hodik.cinema.model.Movie;
import com.ua.hodik.cinema.services.MovieService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FilterFormValidator implements Validator {
    private final MovieService movieService;

    @Autowired
    public FilterFormValidator(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return this.getClass().equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FilterFormDto filterFormDto = (FilterFormDto) target;
        List<LocalDate> date = filterFormDto.getDate();
        if (CollectionUtils.isNotEmpty(date)) {
            if (date.get(0) == null) {
                date.set(0, LocalDate.parse("2000-01-01"));
            }
            if (date.get(1) == null) {
                date.set(1, LocalDate.parse("2100-12-31"));
            }
            if(date.get(0).isAfter(date.get(1))){
                errors.rejectValue("date", "","Invalid date" );
            }
        }
        List<LocalTime> time = filterFormDto.getTime();
        if (CollectionUtils.isNotEmpty(time)) {
            if (time.get(0) == null) {
                time.set(0, LocalTime.MIN);
            }
            if (time.get(1) == null) {
                time.set(1, LocalTime.MAX);
            }
            if (time.get(0).isAfter(time.get(1))) {
                errors.rejectValue("time", "", "Invalid time");
            }
            List<MovieDto> movieDtoList = movieService.findAll(null);
            List<String> moviesNameDb = movieDtoList.stream().map(x -> x.getName()).collect(Collectors.toList());
            List<String> movieNames = filterFormDto.getMovie();
            boolean isAllMoviePresent = movieNames.stream().allMatch(x -> moviesNameDb.contains(x));
            if (!isAllMoviePresent) {
                errors.rejectValue("movie", "", "Invalid movie name");
            }


        }
    }
}





