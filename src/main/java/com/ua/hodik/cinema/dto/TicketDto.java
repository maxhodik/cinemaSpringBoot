package com.ua.hodik.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDto {
    private String userName;
    private LocalDate date;
    private LocalTime time;
    private String movieName;
    private int price;
    private int seats;
    private int account;

    public int getAccount() {
        return price*seats;
    }
}
