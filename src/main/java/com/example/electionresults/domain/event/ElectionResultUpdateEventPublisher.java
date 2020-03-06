package com.example.electionresults.domain.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ElectionResultUpdateEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishUpdateEvent() {
        ElectionResultUpdateEvent electionResultUpdateEvent = new ElectionResultUpdateEvent(this);
        applicationEventPublisher.publishEvent(electionResultUpdateEvent);
    }
}
