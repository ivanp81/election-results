package com.example.electionresults.domain.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ElectionScoreRepository extends CrudRepository<ElectionScore, String> {

    List<ElectionScore> findAllByOrderBySeatsDescVotesDesc();
    ElectionScore findByParty(String code);
}
