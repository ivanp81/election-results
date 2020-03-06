package com.example.electionresults.ingestion.activator;

import com.example.electionresults.domain.event.ElectionResultUpdateEventPublisher;
import com.example.electionresults.domain.service.ElectionScoreService;
import com.example.electionresults.ingestion.transformer.xml.ConstituencyResults;
import com.example.electionresults.ingestion.transformer.xml.Result;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElectionResultContentValidationProcessor {

    Logger logger = LoggerFactory.getLogger(ElectionResultContentValidationProcessor.class);

    @Autowired
    private ElectionScoreService electionScoreService;

    @Autowired
    private ElectionResultUpdateEventPublisher electionResultUpdateEventPublisher;

    @ServiceActivator(inputChannel = "electionResultContentValidationProcessorInputChannel", outputChannel = "electionResultProcessorInputChannel")
    public ConstituencyResults process(Message<ConstituencyResults> msg) throws Exception {

        logger.info("Step 2 - Processing XML validation for constituency " + msg.getPayload().getConstituencyResult().getConstituencyName());

        ConstituencyResults constituencyResults = msg.getPayload();

        List<Result> results = constituencyResults.getConstituencyResult().getResults().getResult();

        for(Result result : results)
            if(result.getVotes() == null)
                throw new Exception("Invalid XML content");

        return constituencyResults;
    }
}