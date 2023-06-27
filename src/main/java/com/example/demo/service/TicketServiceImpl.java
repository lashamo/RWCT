package com.example.demo.service;

import com.example.demo.entity.Ticket;
import com.example.demo.repo.MatchesRepo;
import com.example.demo.repo.TicketRepo;
import com.example.demo.service.dto.TicketResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private static final int MATCHES_MAX_AMOUNT = 48;
    private final TicketRepo ticketRepo;
    private final MatchesRepo matchesRepo;

    public TicketServiceImpl(TicketRepo ticketRepo, MatchesRepo matchesRepo) {
        this.ticketRepo = ticketRepo;
        this.matchesRepo = matchesRepo;
    }

    @Override
    public void addGames(List<Integer> addedTicketIndexes) throws RWCTException {
        for (Integer index : addedTicketIndexes) {
            if (index < MATCHES_MAX_AMOUNT) {
                ticketRepo.save(mapIndexToTicket(index));
            }
        }
    }

    @Override
    public List<TicketResponse> getAllTicket() {
        List<TicketResponse> ticketResponses = new ArrayList<>();
        Iterable<Ticket> iterable = ticketRepo.findAll();
        iterable.forEach(ticket -> ticketResponses.add(mapTicketToTicketResponse(ticket)));
        return ticketResponses;
    }

    private TicketResponse mapTicketToTicketResponse(Ticket ticket) {
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setAddTime(ticket.getAddDate());
        ticketResponse.setMatchId(ticket.getMatches().getGameIndex());
        ticketResponse.setMatchesName(ticket.getMatches().getTeam1() + " vs " + ticket.getMatches().getTeam2());
        return ticketResponse;
    }

    private Ticket mapIndexToTicket(int index) {
        Ticket ticket = new Ticket();
        ticket.setAddDate(LocalDateTime.now());
        ticket.setMatches(matchesRepo.getMatchesByGameIndex(index));
        return ticket;
    }
}
