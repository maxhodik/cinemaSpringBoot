package com.ua.hodik.cinema.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class UserDto {


    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9_-]{3,16}$")
    private String name;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9_-]{5,18}$")
    private String password;


    public UserDto() {
    }

    public UserDto( String name, String password) {

        this.name = name;
        this.password = password;

    }
}
