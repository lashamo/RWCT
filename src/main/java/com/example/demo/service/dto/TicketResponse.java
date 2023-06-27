package com.example.demo.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class TicketResponse {
    private Long id;
    private int matchId;
    private  String matchesName;
    private LocalDateTime localDateTime;
}
