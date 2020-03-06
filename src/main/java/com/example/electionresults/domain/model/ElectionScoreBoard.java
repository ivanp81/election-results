package com.example.electionresults.domain.model;

import java.util.List;

public class ElectionScoreBoard {

    private List<TopScore> topScores;

    public ElectionScoreBoard() {
    }

    public ElectionScoreBoard(List<TopScore> topScores) {
        this.topScores = topScores;
    }

    public List<TopScore> getTopScores() {
        return topScores;
    }
}
