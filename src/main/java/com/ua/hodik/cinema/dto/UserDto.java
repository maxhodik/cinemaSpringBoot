package com.ua.hodik.cinema.dto;

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
public class UserDto {


    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9_-]{3,16}$")
    private String name;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9_-]{5,18}$")
    private String password;


}
