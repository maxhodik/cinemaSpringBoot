package com.ua.hodik.cinema.dto;

import com.ua.hodik.cinema.model.Hall;
import com.ua.hodik.cinema.model.Movie;
import com.ua.hodik.cinema.model.Receipt;
import com.ua.hodik.cinema.model.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionAdminDto {
    private int id;
    private String movieName;
    private LocalDate date;
    private LocalTime time;
    private DayOfWeek dayOfWeek;
    private int hallId;
    private int numberOfAvailableSeats;
    private int numberOfSoldSeats;
    private int capacity;
    private BigDecimal attendance;
    private Status status;


}

