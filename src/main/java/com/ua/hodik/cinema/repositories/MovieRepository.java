package com.ua.hodik.cinema.repositories;

import com.ua.hodik.cinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository <Movie, Integer> {
    Optional<Movie> findByName(String name);
}
