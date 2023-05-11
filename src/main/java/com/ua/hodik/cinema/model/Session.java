package com.ua.hodik.cinema.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    private Hall hall;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "time")
    private LocalTime time;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "session")
    private List<Receipt> receipts;


    public Session(int id, Movie movie, Hall hall, LocalDate date, LocalTime time, Status status) {
        this.id = id;
        this.movie = movie;
        this.hall = hall;
        this.date = date;
        this.time = time;
        this.status = status;

    }


}

