package com.ua.hodik.cinema.repositories;

import com.ua.hodik.cinema.model.Hall;
import com.ua.hodik.cinema.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall, Integer> {



}

