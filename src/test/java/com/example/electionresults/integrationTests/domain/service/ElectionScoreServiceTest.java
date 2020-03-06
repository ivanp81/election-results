package com.example.electionresults.integrationTests.domain.service;

import com.example.electionresults.domain.repository.ElectionScore;
import com.example.electionresults.domain.repository.ElectionScoreRepository;
import com.example.electionresults.domain.service.ElectionScoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ElectionScoreServiceTest {

    @Autowired
    private ElectionScoreService electionScoreService;

    @Autowired
    private ElectionScoreRepository electionScoreRepository;

    @Test
    public void testSearchAdvertise_whenCreateElectionScore() {

        String partyCode = "ABC";
        BigInteger votes = BigInteger.TEN;
        boolean addSeats = true;

        electionScoreService.updateElectionScore(partyCode, votes, addSeats);

        ElectionScore electionScore = electionScoreRepository.findByParty(partyCode);

        assertThat(electionScore.getParty(), equalTo(partyCode));
        assertThat(electionScore.getVotes(), equalTo(votes));
        assertThat(electionScore.getSeats(), equalTo(BigInteger.ONE));
    }

    @Test
    public void testSearchAdvertise_whenUpdateElectionScore() {

        String partyCode = "ABC";
        BigInteger seats = BigInteger.ONE;
        BigInteger votes = BigInteger.TEN;
        boolean addSeats = true;

        ElectionScore actualElectionScore = new ElectionScore(partyCode, seats, votes);
        electionScoreRepository.save(actualElectionScore);

        electionScoreService.updateElectionScore(partyCode, votes, addSeats);

        ElectionScore expectedElectionScore = electionScoreRepository.findByParty(partyCode);

        assertThat(expectedElectionScore.getParty(), equalTo(partyCode));
        assertThat(expectedElectionScore.getVotes(), equalTo(votes.add(votes)));
        assertThat(expectedElectionScore.getSeats(), equalTo(seats.add(seats)));
    }
}

