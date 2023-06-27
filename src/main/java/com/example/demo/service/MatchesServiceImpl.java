package com.example.demo.service;

import com.example.demo.entity.Matches;
import com.example.demo.repo.MatchesRepo;
import com.example.demo.service.dto.MatchesResponse;
import com.example.demo.service.dto.MatchesResponseComparator;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchesServiceImpl implements MatchesService{
    public final MatchesRepo matchesRepo;

    public MatchesServiceImpl(MatchesRepo matchesRepo) {
        this.matchesRepo = matchesRepo;
    }

    @Override
    public List<MatchesResponse> getAllMatchesAndTicket()  {
        List<MatchesResponse> matchesResponses = new ArrayList<>();
        Iterable<Matches> iterable = matchesRepo.findAll();
        iterable.forEach(matches -> matchesResponses.add(mapMatchesToMatchesResponse(matches)));
        Comparator comparator = Collections.reverseOrder(new MatchesResponseComparator());
        Collections.sort(matchesResponses,comparator);
            return matchesResponses;

    }

    @Override
    public List<MatchesResponse> getMatchesByName(String teamName) {
            List<Matches> matches = matchesRepo.getMatchesByTeam(teamName);
            List<MatchesResponse> matchesResponses = new ArrayList<>();
            for (Matches match : matches){
                matchesResponses.add(mapMatchesToMatchesResponse(match));
            }
        return matchesResponses;
    }



    private MatchesResponse mapMatchesToMatchesResponse(Matches matches){
        MatchesResponse matchesResponse = new MatchesResponse();
        matchesResponse.setMatchesName(matches.getTeam1()+ " vs " + matches.getTeam2());
        matchesResponse.setTickets(matches.getTickets().size());
        return matchesResponse;
    }
}
