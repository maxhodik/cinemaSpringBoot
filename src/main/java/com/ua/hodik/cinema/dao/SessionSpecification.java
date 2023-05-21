package com.ua.hodik.cinema.dao;

import com.ua.hodik.cinema.dto.FilterDto;
import com.ua.hodik.cinema.model.Hall;
import com.ua.hodik.cinema.model.Movie;
import com.ua.hodik.cinema.model.Session;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SessionSpecification {


    public Specification<Session> getSessions(Map<String, FilterDto<?>> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Movie, Session> moviesRoot = root.join("movie");
            Join<Hall, Session> hallsRoot = root.join("hall");

//            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//            CriteriaQuery<Session> criteriaQuery = criteriaBuilder.createQuery(Session.class);
//            Root<Session> root = criteriaQuery.from(Session.class);


            FilterDto<?> filterDate = filters.get("date");
            if (filterDate != null) {
                List<?> values = filterDate.getValues();

                Predicate datePredicate = criteriaBuilder.between(root.get("date"),
                        (LocalDate) values.get(0), (LocalDate) values.get(1));
                predicates.add(datePredicate);

            }
            FilterDto<?> filterTime = filters.get("time");
            if (filterTime != null) {
                List<?> values = filterTime.getValues();

                Predicate timePredicate = criteriaBuilder.between(root.get("time"),
                        (LocalTime) values.get(0), (LocalTime) values.get(1));
                predicates.add(timePredicate);

            }
            FilterDto<?> filterMovie = filters.get("movie");
            if (filterMovie != null) {

                CriteriaBuilder.In<String> movie1 = criteriaBuilder.in(moviesRoot.get("name"));
                List<?> movies = filterMovie.getValues();
                for (Object movie : movies) {
                    movie1.value(movie.toString());
                }
                predicates.add(movie1);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}