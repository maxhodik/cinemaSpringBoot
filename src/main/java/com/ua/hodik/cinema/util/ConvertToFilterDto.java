package com.ua.hodik.cinema.util;

import com.ua.hodik.cinema.dto.FilterDto;
import com.ua.hodik.cinema.dto.FilterFormDto;
import com.ua.hodik.cinema.dto.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class ConvertToFilterDto {

    public Map<String, FilterDto<?>> convert(FilterFormDto filterFormDto) {
        Map<String, FilterDto<?>> filters = new HashMap<>();

        if (CollectionUtils.isNotEmpty(filterFormDto.getDate()) && !isAnyNullInList(filterFormDto.getDate())) {
            filters.put("date", new FilterDto<>("date", filterFormDto.getDate(), Operation.BETWEEN));
        }
        if (CollectionUtils.isNotEmpty(filterFormDto.getTime())&& !isAnyNullInList(filterFormDto.getTime())) {
            filters.put("time", new FilterDto<>("time", filterFormDto.getTime(), Operation.BETWEEN));
        }
        if (CollectionUtils.isNotEmpty(filterFormDto.getDay()) && !isAnyNullInList(filterFormDto.getDay())) {
            filters.put("day", new FilterDto<>("day", filterFormDto.getDay(), Operation.IN));
        }
        if (CollectionUtils.isNotEmpty(filterFormDto.getMovie())&& !isAnyNullInList(filterFormDto.getMovie())) {
//            List<String> movieNames = filterFormDto.getMovie().stream().

            filters.put("movie", new FilterDto<>("movie", filterFormDto.getMovie(), Operation.IN));
        }
        if (CollectionUtils.isNotEmpty(filterFormDto.getStatus()) && !isAnyNullInList(filterFormDto.getStatus())) {
            filters.put("status", new FilterDto<>("status", filterFormDto.getStatus(), Operation.IN));
        }
        if (filterFormDto.isAvailableSeats()) {
            filters.put("availableSeats", new FilterDto<>("availableSeats", List.of(filterFormDto.isAvailableSeats()), Operation.IS));
        }
        if (filterFormDto.getDateTime() != null) {
            filters.put("dateTime", new FilterDto<>("dateTime", List.of(filterFormDto.getDateTime()), Operation.AFTER));
        }
        return filters;
    }

    private boolean isAnyNullInList(List<?> values) {
        return values.stream().anyMatch(Objects::isNull);
    }


//    public Map<FilterDto> convert (FilterFormDto filterFormDto) {
//        List<FilterDto> filters = new ArrayList<>();
//
//        if (filterFormDto.getDate() != null) {
//
//            filters.add(new FilterDto("date", List.of(filterFormDto.getDate()), Operation.BETWEEN));
//        }
//        if (filterFormDto.getTime() != null) {
//            filters.add(new FilterDto("time", filterFormDto.getTime(), Operation.BETWEEN));
//        }
//        if (filterFormDto.getDay() != null) {
//            filters.add(new FilterDto("day", filterFormDto.getDay(), Operation.IN));
//        }
//        if (filterFormDto.getMovie() != null) {
//            filters.add(new FilterDto("movie", filterFormDto.getMovie(), Operation.IN));
//        }
//        if (filterFormDto.getStatus() != null) {
//            filters.add(new FilterDto("status", filterFormDto.getStatus(), Operation.IN));
//        }
//        if (filterFormDto.isAvailableSeats()) {
//            filters.add(new FilterDto("availableSeats", List.of(filterFormDto.isAvailableSeats()), Operation.IS));
//        }
//    }
}
