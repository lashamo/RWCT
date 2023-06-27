package com.example.demo.service.dto;

import com.example.demo.entity.Ticket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class MatchesResponse {
    private String matchesName;
    private int tickets;

    @Override
    public String toString(){
        return matchesName + "(" + tickets +")";
    }

}
