package com.ua.hodik.cinema.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hall_id", referencedColumnName = "id")
    private Hall hall;
    @Column(name = "date")
    @DateTimeFormat (pattern = "yyyy/MM/dd")
    private LocalDate date;
    @Column(name = "time")
    @DateTimeFormat (pattern = "hh:mm:ss")
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

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", movie=" + movie +
                ", hall=" + hall +
                ", date=" + date +
                ", time=" + time +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return id == session.id && Objects.equals(movie, session.movie) && Objects.equals(hall, session.hall) && Objects.equals(date, session.date) && Objects.equals(time, session.time) && status == session.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, hall, date, time, status);
    }
}

