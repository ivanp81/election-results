package com.example.electionresults.web;

import com.example.electionresults.domain.model.ElectionScoreBoard;
import com.example.electionresults.domain.service.ElectionScoreBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@Controller
public class ElectionScoreBoardController {

    private ElectionScoreBoardService electionScoreBoardService;

    @Autowired
    public ElectionScoreBoardController(ElectionScoreBoardService electionScoreBoardService) {
        this.electionScoreBoardService = electionScoreBoardService;
    }

    @MessageMapping("/live-election-score")
    @SendTo("/topic/live-election-score")
    public ElectionScoreBoard getElectionScore() {
        return electionScoreBoardService.liveScore();
    }
}
