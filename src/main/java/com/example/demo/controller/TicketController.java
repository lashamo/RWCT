package com.example.demo.controller;

import com.example.demo.service.TicketService;
import com.example.demo.service.dto.TicketResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

@GetMapping("get-all-ticket")
    public List<TicketResponse> getAllTicket(){
        return ticketService.getAllTicket();
    }
}
