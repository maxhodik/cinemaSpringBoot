package com.ua.hodik.cinema.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "movies")
@Data
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    //  private Status status;
    @OneToMany(mappedBy = "movie")
    private List<Session> sessions;

    public Movie(int id, String name, List<Session> sessions) {
        this.id = id;
        this.name = name;
        this.sessions = sessions;
    }
}