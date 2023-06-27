package com.example.demo.service;

import com.example.demo.entity.Matches;
import com.example.demo.entity.Ticket;
import com.example.demo.repo.MatchesRepo;
import com.example.demo.service.dto.MatchTicketResponse;
import com.example.demo.service.dto.MatchesResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchesServiceImpl implements MatchesService {
    public final MatchesRepo matchesRepo;

    public MatchesServiceImpl(MatchesRepo matchesRepo) {
        this.matchesRepo = matchesRepo;
    }

    @Override
    public List<MatchesResponse> getAllMatchesAndTicket(boolean includeTickets) {
        List<MatchesResponse> matchesResponses = new ArrayList<>();
        Iterable<Matches> iterable = matchesRepo.findAll();
        iterable.forEach(matches -> matchesResponses.add(mapMatchesToMatchesResponse(matches, includeTickets)));
        Comparator<MatchesResponse> comparator = Collections.reverseOrder(new MatchesResponseComparator());
        matchesResponses.sort(comparator);
        return matchesResponses;
    }

    @Override
    public List<MatchesResponse> getMatchesByName(String teamName, boolean includeTickets) {
        List<Matches> matches = matchesRepo.getMatchesByTeam(teamName);
        List<MatchesResponse> matchesResponses = new ArrayList<>();
        for (Matches match : matches) {
            matchesResponses.add(mapMatchesToMatchesResponse(match, includeTickets));
        }
        return matchesResponses;
    }

    private MatchesResponse mapMatchesToMatchesResponse(Matches matches, boolean includeTickets) {
        MatchesResponse matchesResponse = new MatchesResponse();
        matchesResponse.setMatchesName(matches.getTeam1() + " vs " + matches.getTeam2());
        matchesResponse.setTicketsAmount(matches.getTickets().size());

        if (includeTickets) {
            matchesResponse.setTickets(new ArrayList<>());
            for (Ticket ticket : matches.getTickets()){
                matchesResponse.getTickets().add(mapTicketToMatchesTicketResponse(ticket));
            }
        }

        return matchesResponse;
    }
    private MatchTicketResponse mapTicketToMatchesTicketResponse(Ticket ticket){
        MatchTicketResponse matchTicketResponse = new MatchTicketResponse();
        matchTicketResponse.setId(ticket.getId());
        matchTicketResponse.setAddTime(ticket.getAddDate());
        return matchTicketResponse;
    }
}
