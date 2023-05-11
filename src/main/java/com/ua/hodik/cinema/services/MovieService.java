package com.ua.hodik.cinema.services;

import com.ua.hodik.cinema.model.Movie;
import com.ua.hodik.cinema.repositories.MovieRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAll(String orderBy) {
        if (orderBy == null) {
            return movieRepository.findAll();
        }
        return movieRepository.findAll(Sort.by(orderBy));
    }

    public Optional<Movie> findByName(String name) {
        return movieRepository.findByName(name);
    }

    @Transactional
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    @Transactional
    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
    }

    public Optional<Movie> findById(int id) {
       return movieRepository.findById(id);
    }
}
