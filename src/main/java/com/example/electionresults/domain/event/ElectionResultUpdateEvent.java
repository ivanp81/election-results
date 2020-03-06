package com.example.electionresults.domain.event;

import org.springframework.context.ApplicationEvent;

public class ElectionResultUpdateEvent extends ApplicationEvent {

    public ElectionResultUpdateEvent(Object source) {
        super(source);
    }
}

