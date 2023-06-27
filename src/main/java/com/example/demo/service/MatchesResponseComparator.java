package com.example.demo.service;

import com.example.demo.service.dto.MatchesResponse;

import java.util.Comparator;

public class MatchesResponseComparator implements Comparator<MatchesResponse> {
    @Override
    public int compare(MatchesResponse a, MatchesResponse b) {
        return a.getTicketsAmount()-b.getTicketsAmount();
    }
}
