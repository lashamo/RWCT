package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Matches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String team1;
    private String team2;
    private int gameIndex;
    @OneToMany(mappedBy = "matches")
    private List<Ticket> tickets = new ArrayList<>();
}
