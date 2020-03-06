package com.example.electionresults.domain.repository;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "ELECTION_SCORE")
public class ElectionScore {

    @Id
    private String party;
    private BigInteger seats;
    private BigInteger votes;

    public ElectionScore() {
    }

    public ElectionScore(String party, BigInteger seats, BigInteger votes) {
        this.party = party;
        this.seats = seats;
        this.votes = votes;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public BigInteger getSeats() {
        return seats;
    }

    public void setSeats(BigInteger seats) {
        this.seats = seats;
    }

    public BigInteger getVotes() {
        return votes;
    }

    public void setVotes(BigInteger votes) {
        this.votes = votes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElectionScore that = (ElectionScore) o;
        return Objects.equals(party, that.party) &&
                Objects.equals(seats, that.seats) &&
                Objects.equals(votes, that.votes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(party, seats, votes);
    }
}
