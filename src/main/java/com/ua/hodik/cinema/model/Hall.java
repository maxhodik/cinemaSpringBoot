package com.ua.hodik.cinema.model;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Builder
@Table(name = "halls")

public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "number_seats")
    private int capacity;
    @Column(name = "number_available_seats")
    private int numberAvailableSeats;
    @Column(name = "number_sold_seats")
    private int numberOfSoldSeats;
    @Column(name = "attendance")
    private BigDecimal attendance;

    @OneToMany(mappedBy = "hall")
    private List<Session> sessions;

    public Hall() {
    }

    public Hall(int id, int capacity, int numberAvailableSeats, int numberOfSoldSeats, BigDecimal attendance, List<Session> sessions) {
        this.id = id;
        this.capacity = capacity;
        this.numberAvailableSeats = numberAvailableSeats;
        this.numberOfSoldSeats = numberOfSoldSeats;
        this.attendance = attendance;
        this.sessions = sessions;
    }
}
