package com.ua.hodik.cinema.dto;

import com.ua.hodik.cinema.model.Session;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallDto {

    private int id;
    @Min(value = 1)
    @Max(value = 25)
    private int capacity;

    private int numberAvailableSeats;

    private int numberOfSoldSeats;

    private BigDecimal attendance;

    private List<Session> sessions;
}
