package com.ua.hodik.cinema.dao;

import com.ua.hodik.cinema.dto.FilterDto;
import com.ua.hodik.cinema.model.Hall;
import com.ua.hodik.cinema.model.Movie;
import com.ua.hodik.cinema.model.Session;
import com.ua.hodik.cinema.model.Status;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
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
//filter for DAY of week
            FilterDto<?> filterDay = filters.get("day");
            if (filterDay != null) {
                List<?> values = filterDay.getValues();
                Expression<Integer> dayOfWeekExp = criteriaBuilder.function("DAYOFWEEK", Integer.class, root.get("date"));


                CriteriaBuilder.In<Integer> dayCriteria = criteriaBuilder.in(dayOfWeekExp);
                for (Object day : values) {
                    dayCriteria.value(Integer.valueOf(day.toString()));
                }
                predicates.add(dayCriteria);

            }

            FilterDto<?> filterStatus = filters.get("status");
            if (filterStatus != null) {
                List<?> values = filterStatus.getValues();
                Status status = Status.valueOf(values.get(0).toString());
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"),
                        status);
                predicates.add(statusPredicate);

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
            //filter for DataTime.now
            FilterDto<?> filterDateTime = filters.get("dateTime");
            if (filterDateTime != null) {
                List<?> values = filterDateTime.getValues();

                Expression<String> dateExp = criteriaBuilder.function("DATE", String.class, root.get("date"));
                Expression<String> timeExp = criteriaBuilder.function("TIME", String.class, root.get("time"));
            Expression<String> stringExpression = criteriaBuilder.concat(dateExp, timeExp);
            String dateTimeExp= values.get(0).toString().replace("T", "");
                System.out.println(dateTimeExp);
                Predicate dateTimePredicate = criteriaBuilder.greaterThan(stringExpression,
                         dateTimeExp);

                predicates.add(dateTimePredicate);

            }
            FilterDto<?> filterAvailableSeats = filters.get("availableSeats");
            if (filterAvailableSeats != null) {
                Predicate availableSeatsPredicate = criteriaBuilder.greaterThan(hallsRoot.get("numberAvailableSeats"),
                        0);
                predicates.add(availableSeatsPredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
