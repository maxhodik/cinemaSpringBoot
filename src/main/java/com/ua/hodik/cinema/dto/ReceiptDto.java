package com.ua.hodik.cinema.dto;

import com.ua.hodik.cinema.model.Session;
import com.ua.hodik.cinema.model.State;
import com.ua.hodik.cinema.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiptDto {

    private int id;

    private String userName;

    private int sessionId;
    private String movieName;

    private State state;

    private int numberOfSeats;

    private int price;
}
