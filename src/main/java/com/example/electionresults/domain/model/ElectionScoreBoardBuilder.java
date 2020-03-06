package com.example.electionresults.domain.model;

import com.example.electionresults.domain.repository.ElectionScore;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ElectionScoreBoardBuilder {

    private List<ElectionScore> electionScores;
    private int topScoresSize;
    private String othersPartyLabel;
    private int seatsForMajority;
    private BigInteger currentTotalVotes;

    public ElectionScoreBoardBuilder() {
        this.electionScores = new ArrayList();
    }

    public ElectionScoreBoardBuilder withElectionScore(List<ElectionScore> electionScores) {

        this.electionScores = electionScores;
        return this;
    }

    public ElectionScoreBoardBuilder withTopScoreSize(int topScoresSize) {

        this.topScoresSize = topScoresSize;
        return this;
    }

    public ElectionScoreBoardBuilder withOthersPartyLabel(String othersPartyLabel) {

        this.othersPartyLabel = othersPartyLabel;
        return this;
    }

    public ElectionScoreBoardBuilder withTotalSeats(int totalSeats) {
        this.seatsForMajority = (totalSeats / 2) + 1;
        return this;
    }

    public ElectionScoreBoard build() {

        this.currentTotalVotes = electionScores.stream().map(ElectionScore::getVotes).reduce(BigInteger.ZERO, BigInteger::add);

        List<TopScore> topScores = buildTopScores();
        ElectionScoreBoard electionScoreBoard = new ElectionScoreBoard(topScores);
        return electionScoreBoard;
    }

    private List<TopScore> buildTopScores() {

        List<ElectionScore> topElectionScores = electionScores.size() > this.topScoresSize ? electionScores.stream().limit(this.topScoresSize).collect(Collectors.toList()) : electionScores;

        List<TopScore> topScores = new ArrayList();

        for(ElectionScore electionScore : topElectionScores) {

            TopScore topScore = new TopScore(electionScore, currentShare(electionScore.getVotes()), isMajorityParty(electionScore.getParty()));
            topScores.add(topScore);
        }

        if(electionScores.size() > topScoresSize)
            topScores.add(getOthers(electionScores));

        return topScores;
    }

    private TopScore getOthers(List<ElectionScore> electionScores) {

        List<ElectionScore> others = electionScores.subList(this.topScoresSize, electionScores.size());
        ElectionScore other = reduceOthers(others);

        return new TopScore(other, currentShare(other.getVotes()), Boolean.FALSE);

    }

    private ElectionScore reduceOthers(List<ElectionScore> others) {

        BigInteger totalSeats = BigInteger.ZERO;
        BigInteger totalVotes = BigInteger.ZERO;

        for(ElectionScore other: others) {

            totalSeats = totalSeats.add(other.getSeats());
            totalVotes = totalVotes.add(other.getVotes());
        }

        ElectionScore other = new ElectionScore(this.othersPartyLabel, totalSeats, totalVotes);
        return other;
    }

    private BigDecimal currentShare(BigInteger currentTotalPartyVotes) {

        if(!this.currentTotalVotes.equals(BigDecimal.ZERO))
           return new BigDecimal(currentTotalPartyVotes).divide(new BigDecimal(currentTotalVotes), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100.0"));

        return BigDecimal.ZERO;
    }

    private Boolean isMajorityParty(String partyCode) {

        if(!electionScores.isEmpty()) {

            ElectionScore maxSeatsParty = electionScores.stream().max(Comparator.comparing(ElectionScore::getSeats)).get();

            if(maxSeatsParty.getParty().equals(partyCode) && maxSeatsParty.getSeats().intValue() >= this.seatsForMajority)
                return true;
        }

        return false;
    }
}
