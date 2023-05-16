package com.ua.hodik.cinema.dto;

import com.ua.hodik.cinema.model.Session;
import jakarta.persistence.*;
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

    private int capacity;

    private int numberAvailableSeats;

    private int numberOfSoldSeats;

    private BigDecimal attendance;

    private List<Session> sessions;
}
