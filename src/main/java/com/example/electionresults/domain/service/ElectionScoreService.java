package com.example.electionresults.domain.service;

import java.math.BigInteger;

public interface ElectionScoreService {

    void updateElectionScore(String partyCode, BigInteger votes, boolean addSeats);
}
