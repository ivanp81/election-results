package com.example.electionresults.unitTests.ingestion.activator;

import com.example.electionresults.domain.service.ElectionScoreService;
import com.example.electionresults.ingestion.activator.ElectionResultProcessor;
import com.example.electionresults.ingestion.transformer.xml.ConstituencyResult;
import com.example.electionresults.ingestion.transformer.xml.ConstituencyResults;
import com.example.electionresults.ingestion.transformer.xml.Result;
import com.example.electionresults.ingestion.transformer.xml.Results;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.math.BigInteger;
import java.util.Arrays;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ElectionResultProcessorTest {

    private ElectionResultProcessor electionResultProcessor;

    @Mock
    private ElectionScoreService electionScoreService;

    private Result result;

    @Before
    public void setUp() {
        initMocks(this);
        electionResultProcessor = new ElectionResultProcessor(electionScoreService);
    }

    @Test
    public void testProcess() {

        Message<ConstituencyResults> message = buildDummyMessage();
        electionResultProcessor.process(message);

        verify(electionScoreService, times(1)).updateElectionScore(result.getPartyCode(), result.getVotes(), true);
    }

    private Message<ConstituencyResults> buildDummyMessage() {

        result = new Result(BigInteger.TEN, "ABC", null);
        Results results = new Results(Arrays.asList(result));
        ConstituencyResult constituencyResult = new ConstituencyResult(null, null, null, results);
        ConstituencyResults constituencyResults = new ConstituencyResults(constituencyResult);

        Message<ConstituencyResults> message = MessageBuilder.withPayload(constituencyResults).build();

        return message;

    }
}