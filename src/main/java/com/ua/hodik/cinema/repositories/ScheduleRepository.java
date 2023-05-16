package com.ua.hodik.cinema.repositories;

import com.ua.hodik.cinema.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Session, Integer> {

}
