package com.example.electionresults.unitTests.ingestion.activator;

import com.example.electionresults.ingestion.activator.ElectionResultContentValidationProcessor;
import com.example.electionresults.ingestion.transformer.xml.ConstituencyResult;
import com.example.electionresults.ingestion.transformer.xml.ConstituencyResults;
import com.example.electionresults.ingestion.transformer.xml.Result;
import com.example.electionresults.ingestion.transformer.xml.Results;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

public class ElectionResultContentValidationProcessorTest {

    private ElectionResultContentValidationProcessor electionResultContentValidationProcessor;

    @Before
    public void setUp() {
        electionResultContentValidationProcessor = new ElectionResultContentValidationProcessor();
    }

    @Test
    public void testProcess() throws Exception {

        Message<ConstituencyResults> message = buildDummyMessage(BigInteger.ONE);
        ConstituencyResults constituencyResults = electionResultContentValidationProcessor.process(message);

        assertNotNull(constituencyResults);
    }

    @Test(expected = Exception.class)
    public void testProcess_whenResultWithNullVotesFound() throws Exception{

        Message<ConstituencyResults> message = buildDummyMessage(null);
        electionResultContentValidationProcessor.process(message);
    }

    private Message<ConstituencyResults> buildDummyMessage(BigInteger votes) {

        Result result = new Result(votes, "ABC", null);
        Results results = new Results(Arrays.asList(result));
        ConstituencyResult constituencyResult = new ConstituencyResult(null, null, null, results);
        ConstituencyResults constituencyResults = new ConstituencyResults(constituencyResult);

        Message<ConstituencyResults> message = MessageBuilder.withPayload(constituencyResults).build();

        return message;
    }
}