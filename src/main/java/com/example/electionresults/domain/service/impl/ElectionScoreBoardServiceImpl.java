package com.example.electionresults.domain.service.impl;

import com.example.electionresults.domain.model.ElectionScoreBoardBuilder;
import com.example.electionresults.domain.repository.Election;
import com.example.electionresults.domain.repository.ElectionRepository;
import com.example.electionresults.domain.repository.ElectionScore;
import com.example.electionresults.domain.model.ElectionScoreBoard;
import com.example.electionresults.domain.repository.ElectionScoreRepository;
import com.example.electionresults.domain.service.ElectionScoreBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectionScoreBoardServiceImpl implements ElectionScoreBoardService {

    @Autowired
    private ElectionScoreRepository electionScoreRepository;

    @Autowired
    private ElectionRepository electionRepository;

    public ElectionScoreBoard liveScore() {

        Election election = electionRepository.findByActive(Boolean.TRUE);

        List<ElectionScore> electionScores = electionScoreRepository.findAllByOrderBySeatsDescVotesDesc();

        ElectionScoreBoard electionScoreBoard = new ElectionScoreBoardBuilder()
                .withElectionScore(electionScores)
                .withTopScoreSize(3)
                .withOthersPartyLabel("OTHERS")
                .withTotalSeats(election.getTotalSeats().intValue())
                .build();

        return electionScoreBoard;
    }
}
