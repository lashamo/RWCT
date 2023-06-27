package com.example.demo.service;

import com.example.demo.service.dto.MatchesResponse;

import java.util.List;

public interface MatchesService {
    List<MatchesResponse> getAllMatchesAndTicket(boolean includeTickets);
    List<MatchesResponse> getMatchesByName(String teamName, boolean includeTickets);
}
