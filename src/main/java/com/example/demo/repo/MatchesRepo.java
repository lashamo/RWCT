package com.example.demo.repo;

import com.example.demo.entity.Matches;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchesRepo extends CrudRepository<Matches,Long> {
    Matches getMatchesByGameIndex(int index);
    @Query("SELECT m from Matches  m  WHERE m.team1 = :teamName or m.team2 = :teamName")
    List<Matches> getMatchesByTeam(String teamName);
}
