package com.example.demo.controller;

import com.example.demo.service.MatchesService;
import com.example.demo.service.dto.MatchesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MatchesController {
    private final MatchesService matchesService;

    public MatchesController(MatchesService matchesService) {
        this.matchesService = matchesService;
    }

    @GetMapping("get-by-name-matches")
    private List<MatchesResponse> getMatchesByName(@RequestParam(required = false) String teamName,
                                                   @RequestParam(required = false) boolean includeTickets) {
        if (teamName != null) {
            return matchesService.getMatchesByName(teamName, includeTickets);
        } else {
            return matchesService.getAllMatchesAndTicket(includeTickets);
        }
    }
}
