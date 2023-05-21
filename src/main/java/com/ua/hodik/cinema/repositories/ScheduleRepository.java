package com.ua.hodik.cinema.repositories;

import com.ua.hodik.cinema.model.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Session, Integer>, JpaSpecificationExecutor<Session> {


    Page<Session> findAll(Specification<Session> sessionSpecification, Pageable pageable);
}

