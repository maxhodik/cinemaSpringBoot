package com.ua.hodik.cinema.services;

import com.ua.hodik.cinema.dto.MovieDto;
import com.ua.hodik.cinema.model.Movie;
import com.ua.hodik.cinema.repositories.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class MovieService {
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    public MovieService(MovieRepository movieRepository, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }

    public List<MovieDto> findAll(String orderBy) {
        if (orderBy == null) {

            return convertListToMovieDtoList(movieRepository.findAll());
        }
        return convertListToMovieDtoList(movieRepository.findAll(Sort.by(orderBy)));
    }

    public MovieDto findByName(String name) {
        Movie movie = movieRepository.findByName(name).orElse(null);
        //TODO smth
        if(movie==null){return null;}
        return convertToMovieDto(movie);

    }


    public void save(MovieDto movieDto) {
        movieRepository.save(convertToMovie(movieDto));
    }


    public void deleteMovie(int id) {
        movieRepository.deleteById(id);
    }

    public MovieDto findById(int id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        return convertToMovieDto(movie);


    }


    public Page<MovieDto> findAllPageable(Pageable pageable) {

        return movieRepository.findAll(pageable).map(x -> convertToMovieDto(x));


    }

    private List<MovieDto> convertListToMovieDtoList(List<Movie> movies) {
        return movies.stream().map(x -> convertToMovieDto(x)).collect(Collectors.toList());
    }

    private Movie convertToMovie(MovieDto movieDto) {
        return modelMapper.map(movieDto, Movie.class);
    }

    private MovieDto convertToMovieDto(Movie movie) {
        return modelMapper.map(movie, MovieDto.class);
    }
}
