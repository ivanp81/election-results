package com.example.electionresults.ingestion.activator;

import com.example.electionresults.domain.event.ElectionResultUpdateEventPublisher;
import com.example.electionresults.domain.service.ElectionScoreService;
import com.example.electionresults.ingestion.transformer.xml.ConstituencyResults;
import com.example.electionresults.ingestion.transformer.xml.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElectionResultUpdateNotifierProcessor {

    Logger logger = LoggerFactory.getLogger(ElectionResultUpdateNotifierProcessor.class);

    @Autowired
    private ElectionResultUpdateEventPublisher electionResultUpdateEventPublisher;

    @Autowired
    public ElectionResultUpdateNotifierProcessor(ElectionResultUpdateEventPublisher electionResultUpdateEventPublisher) {
        this.electionResultUpdateEventPublisher = electionResultUpdateEventPublisher;
    }

    @ServiceActivator(inputChannel = "electionResultUpdateNotifierProcessorInputChannel", outputChannel = "nullChannel")
    public void process(Message<ConstituencyResults> msg) {

        logger.info("Step 4 - Notify new election results for constituency " + msg.getPayload().getConstituencyResult().getConstituencyName());
        electionResultUpdateEventPublisher.publishUpdateEvent();
    }
}