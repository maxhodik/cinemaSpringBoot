package com.ua.hodik.cinema.dto;

import com.ua.hodik.cinema.model.Status;
import com.ua.hodik.cinema.util.validation.ValidTime;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionAdminDto {
    private int id;
    @NotEmpty
    @Pattern(regexp = "^[A-Za-zА-Яа-яіїєґ0-9_]+[A-zA-zА-Яа-яіїєґ0-9_ '-`!?.,-:&;]*", message = "Invalid movie name")
    private String movieName;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @NotNull
    @FutureOrPresent
    private LocalDate date;
    @DateTimeFormat(pattern = "hh:mm:ss")
    @NotNull
    @ValidTime
    private LocalTime time;
    private DayOfWeek dayOfWeek;
    private int hallId;
    private int numberOfAvailableSeats;
    private int numberOfSoldSeats;
    @Max(value = 25)
    @Min(value = 1)
    private int capacity;
    private BigDecimal attendance;
    private Status status;

}

