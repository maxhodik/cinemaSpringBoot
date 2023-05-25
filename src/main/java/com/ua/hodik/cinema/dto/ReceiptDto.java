package com.ua.hodik.cinema.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(value = 1)
    @Max(value = 5)
    private int numberOfSeats;

    private int price;
}
