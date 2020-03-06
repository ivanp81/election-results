package com.example.electionresults.domain.service.impl;

import com.example.electionresults.domain.repository.ElectionScore;
import com.example.electionresults.domain.repository.ElectionScoreRepository;
import com.example.electionresults.domain.service.ElectionScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class ElectionScoreServiceImpl implements ElectionScoreService {

    @Autowired
    private ElectionScoreRepository electionScoreRepository;

    public void updateElectionScore(String partyCode, BigInteger votes, boolean addSeats) {

        ElectionScore electionScore = electionScoreRepository.findByParty(partyCode);

        if(electionScore == null)
            electionScore = new ElectionScore(partyCode, BigInteger.ZERO, BigInteger.ZERO);

        BigInteger currentSeats = addSeats ? electionScore.getSeats().add(BigInteger.ONE) : electionScore.getSeats();
        BigInteger currentVotes = electionScore.getVotes().add(votes);

        electionScore.setSeats(currentSeats);
        electionScore.setVotes(currentVotes);

        electionScoreRepository.save(electionScore);
    }
}
