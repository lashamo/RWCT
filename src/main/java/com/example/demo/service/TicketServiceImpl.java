package com.example.demo.service;

import com.example.demo.TicketsValidatorJob;
import com.example.demo.entity.Ticket;
import com.example.demo.repo.MatchesRepo;
import com.example.demo.repo.TicketRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepo ticketRepo;
    private final MatchesRepo matchesRepo;
    public TicketServiceImpl(TicketRepo ticketRepo, MatchesRepo matchesRepo) {
        this.ticketRepo = ticketRepo;
        this.matchesRepo = matchesRepo;
    }

    @Override
    public void addGames(List<Integer> addedTicketIndexes) throws RWCTException {
        for (Integer index : addedTicketIndexes){
            if (index<48){
                ticketRepo.save(mapIndexToTicket(index));
            }

        }
    }

    private Ticket mapIndexToTicket(int index){
        Ticket ticket = new Ticket();
        ticket.setAddDate(LocalDateTime.now());
        ticket.setMatches(matchesRepo.getMatchesByGameIndex(index));
        return ticket;

    }




}
