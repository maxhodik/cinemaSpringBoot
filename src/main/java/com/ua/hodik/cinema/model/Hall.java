package com.ua.hodik.cinema.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "halls")
@AllArgsConstructor
@NoArgsConstructor

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

}
