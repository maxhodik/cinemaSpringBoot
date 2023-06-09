package com.ua.hodik.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterFormDto {
    private LocalDateTime dateTime;
    private List<LocalDate> date;
    private List<LocalTime> time;
    private List<String> day;
    private List<String> movie;
    private List<String> status;
    private boolean availableSeats;

}
