package com.example.electionresults.domain.event;

import com.example.electionresults.domain.service.ElectionScoreBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ElectionResultUpdateEventListener implements ApplicationListener<ElectionResultUpdateEvent> {

    private SimpMessagingTemplate messageTemplate;

    private ElectionScoreBoardService electionScoreBoardService;

    @Autowired
    public ElectionResultUpdateEventListener(SimpMessagingTemplate messageTemplate, ElectionScoreBoardService electionScoreBoardService) {
        this.messageTemplate = messageTemplate;
        this.electionScoreBoardService = electionScoreBoardService;
    }

    @Override
    public void onApplicationEvent(ElectionResultUpdateEvent event) {
        messageTemplate.convertAndSend("/topic/live-election-score", electionScoreBoardService.liveScore());
    }
}
