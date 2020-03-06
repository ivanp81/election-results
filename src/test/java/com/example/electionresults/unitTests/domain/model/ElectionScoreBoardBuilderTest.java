package com.example.electionresults.unitTests.domain.model;

import com.example.electionresults.domain.model.ElectionScoreBoard;
import com.example.electionresults.domain.model.ElectionScoreBoardBuilder;
import com.example.electionresults.domain.model.TopScore;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

import com.example.electionresults.domain.repository.ElectionScore;

public class ElectionScoreBoardBuilderTest {

    private String otherPartyLabel = "OTHERS";

    @Test
    public void testElectionScoreBoardBuilder() {

        List<ElectionScore> electionScores = dummyElectionScores();

        ElectionScoreBoard electionScoreBoard = new ElectionScoreBoardBuilder()
                .withElectionScore(electionScores)
                .withTopScoreSize(2)
                .withOthersPartyLabel(otherPartyLabel)
                .withTotalSeats(3)
                .build();

        List<TopScore> topScores = electionScoreBoard.getTopScores();

        assertEquals(topScores.get(0).getParty(), "ABC");
        assertEquals(topScores.get(0).isMajority(), true);
        assertEquals(topScores.get(0).getShare(), new BigDecimal("50.00000"));

        assertEquals(topScores.get(1).getParty(), "CDE");
        assertEquals(topScores.get(1).isMajority(), false);
        assertEquals(topScores.get(1).getShare(), new BigDecimal("25.00000"));

        assertEquals(topScores.get(2).getParty(), otherPartyLabel);
        assertEquals(topScores.get(2).isMajority(), false);
        assertEquals(topScores.get(2).getShare(),  new BigDecimal("25.00000"));
    }

    @Test
    public void testElectionScoreBoardBuilder_whenTopScoreSizeGreatherThanElectionScores() {

        List<ElectionScore> electionScores = dummyElectionScores();

        ElectionScoreBoard electionScoreBoard = new ElectionScoreBoardBuilder()
                .withElectionScore(electionScores)
                .withTopScoreSize(3)
                .withOthersPartyLabel(otherPartyLabel)
                .withTotalSeats(3)
                .build();

        List<TopScore> topScores = electionScoreBoard.getTopScores();

        assertEquals(topScores.get(0).getParty(), "ABC");
        assertEquals(topScores.get(0).isMajority(), true);
        assertEquals(topScores.get(0).getShare(), new BigDecimal("50.00000"));

        assertEquals(topScores.get(1).getParty(), "CDE");
        assertEquals(topScores.get(1).isMajority(), false);
        assertEquals(topScores.get(1).getShare(), new BigDecimal("25.00000"));

        assertNotEquals(topScores.get(2).getParty(), otherPartyLabel);
        assertEquals(topScores.get(2).getParty(), "EFG");
        assertEquals(topScores.get(2).isMajority(), false);
        assertEquals(topScores.get(2).getShare(),  new BigDecimal("25.00000"));
    }

    private List<ElectionScore> dummyElectionScores() {

        ElectionScore electionScore1 = new ElectionScore("ABC", new BigInteger("2"), new BigInteger("10") );
        ElectionScore electionScore2 = new ElectionScore("CDE", new BigInteger("1"), new BigInteger("5") );
        ElectionScore electionScore3 = new ElectionScore("EFG", new BigInteger("0"), new BigInteger("5") );

       return Arrays.asList(electionScore1, electionScore2, electionScore3);
    }
}

