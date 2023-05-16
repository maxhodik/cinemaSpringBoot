package com.ua.hodik.cinema.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "hall")
    private List<Session> sessions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return id == hall.id && capacity == hall.capacity && numberAvailableSeats == hall.numberAvailableSeats && numberOfSoldSeats == hall.numberOfSoldSeats && Objects.equals(attendance, hall.attendance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacity, numberAvailableSeats, numberOfSoldSeats, attendance);
    }

    @Override
    public String toString() {
        return "Hall{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", numberAvailableSeats=" + numberAvailableSeats +
                ", numberOfSoldSeats=" + numberOfSoldSeats +
                ", attendance=" + attendance +
                '}';
    }
}
