package com.example.electionresults.integrationTests.domain.event.config;

import com.example.electionresults.domain.event.ElectionResultUpdateEvent;
import org.springframework.context.ApplicationListener;

public class DummyElectionResultUpdateEventListener implements ApplicationListener<ElectionResultUpdateEvent> {

    private Integer counter;

    public void onApplicationEvent(ElectionResultUpdateEvent event) {
        counter++;
    }

    public void initCounter() {
        counter = new Integer(0);
    }

    public Integer getCounter() {
        return counter;
    }
}
