package com.example.demo.service;

import com.example.demo.service.dto.TicketResponse;

import java.util.List;

public interface TicketService {
     void addGames(List<Integer> addedTicketIndexes) throws RWCTException;
     List<TicketResponse> getAllTicket();
}
