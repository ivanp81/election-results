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

import java.util.Comparator;
import java.util.List;

@Component
public class ElectionResultProcessor {

    Logger logger = LoggerFactory.getLogger(ElectionResultProcessor.class);

    private ElectionScoreService electionScoreService;

    @Autowired
    public ElectionResultProcessor(ElectionScoreService electionScoreService) {
        this.electionScoreService = electionScoreService;
    }

    @ServiceActivator(inputChannel = "electionResultProcessorInputChannel", outputChannel = "electionResultUpdateNotifierProcessorInputChannel")
    public ConstituencyResults process(Message<ConstituencyResults> msg) {

        logger.info("Step 3 - Processing results for constituency " + msg.getPayload().getConstituencyResult().getConstituencyName());

        ConstituencyResults constituencyResults = msg.getPayload();
        List<Result> results = constituencyResults.getConstituencyResult().getResults().getResult();

        String maxVotesParty = results.stream().max(Comparator.comparing(Result::getVotes)).get().getPartyCode();

        results.stream().forEach(result -> electionScoreService.updateElectionScore(result.getPartyCode(), result.getVotes(), result.getPartyCode().equals(maxVotesParty)));

        return constituencyResults;
    }
}