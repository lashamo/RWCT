package com.example.demo.repo;

import com.example.demo.entity.Matches;
import org.springframework.data.repository.CrudRepository;

public interface MatchesRepo extends CrudRepository<Matches,Long> {
    Matches getMatchesByGameIndex(int index);
}
