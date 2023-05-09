package com.ua.hodik.cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "login")
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9_-]{3,16}$")
    private String name;
    @Column(name = "password")
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9_-]{5,18}$")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Receipt> receipts;

    public User() {
    }

    public User(int id, String name, String password, Role role, List<Receipt> receipts) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.receipts = receipts;
    }
}
