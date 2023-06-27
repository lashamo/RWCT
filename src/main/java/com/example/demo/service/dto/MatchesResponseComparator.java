package com.example.demo.service.dto;

import java.util.Comparator;

public class MatchesResponseComparator implements Comparator<MatchesResponse> {
    @Override
    public int compare(MatchesResponse a, MatchesResponse b) {
        return a.getTickets()-b.getTickets();
    }
}
