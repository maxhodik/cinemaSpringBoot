package com.ua.hodik.cinema.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private int id;
    @NotEmpty
    @Pattern(regexp = "^[A-Za-zА-Яа-яіїєґ0-9_]+[A-zA-zА-Яа-яіїєґ0-9_ '-`!?.,-:&;]*", message = "Invalid movie name")
    private String name;

    @Override
    public String toString() {
        return "MovieDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
