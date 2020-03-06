package com.example.electionresults.unitTests.ingestion.activator;

import com.example.electionresults.domain.event.ElectionResultUpdateEventPublisher;
import com.example.electionresults.domain.service.ElectionScoreService;
import com.example.electionresults.ingestion.activator.ElectionResultProcessor;
import com.example.electionresults.ingestion.activator.ElectionResultUpdateNotifierProcessor;
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

public class ElectionResultUpdateNotifierProcessorTest {

    private ElectionResultUpdateNotifierProcessor electionResultUpdateNotifierProcessor;

    @Mock
    private ElectionResultUpdateEventPublisher electionResultUpdateEventPublisher;

    private Result result;

    @Before
    public void setUp() {
        initMocks(this);
        electionResultUpdateNotifierProcessor = new ElectionResultUpdateNotifierProcessor(electionResultUpdateEventPublisher);
    }

    @Test
    public void testProcess() {

        Message<ConstituencyResults> message = buildDummyMessage();
        electionResultUpdateNotifierProcessor.process(message);

        verify(electionResultUpdateEventPublisher, times(1)).publishUpdateEvent();
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