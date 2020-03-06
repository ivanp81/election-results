package com.example.electionresults.integrationTests.domain.event.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyEventListenerConfig {

    @Bean
    public DummyElectionResultUpdateEventListener dummyEventListener() {
        return new DummyElectionResultUpdateEventListener();
    }
}
