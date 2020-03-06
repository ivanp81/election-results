package com.example.electionresults.domain.model;

import com.example.electionresults.domain.repository.ElectionScore;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TopScore {

    private String party;
    private BigInteger seats;
    private BigInteger votes;
    private BigDecimal share;
    private Boolean isMajority;

    public TopScore() {
    }

    public TopScore(ElectionScore electionScore, BigDecimal share, Boolean isMajority) {
        this.party = electionScore.getParty();
        this.seats = electionScore.getSeats();
        this.votes = electionScore.getVotes();
        this.share = share;
        this.isMajority = isMajority;
    }

    public String getParty() {
        return party;
    }

    public BigInteger getSeats() {
        return seats;
    }

    public BigDecimal getShare() {
        return share;
    }

    public BigInteger getVotes() {
        return votes;
    }

    public Boolean isMajority() {
        return isMajority;
    }
}
