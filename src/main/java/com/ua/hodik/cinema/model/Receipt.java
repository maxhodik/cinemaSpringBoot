package com.ua.hodik.cinema.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


@Entity
@Table(name = "orders")
@Data
@Builder
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "sessions_id", referencedColumnName = "id")
    private Session session;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;
    @Column(name = "number_of_seats")
    private int numberOfSeats;
    @Column(name = "price")
//    @Value("${price}")
    private int price;
}
