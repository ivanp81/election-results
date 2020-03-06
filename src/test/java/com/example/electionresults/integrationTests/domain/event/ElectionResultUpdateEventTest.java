package com.example.electionresults.integrationTests.domain.event;

import com.example.electionresults.domain.event.ElectionResultUpdateEventPublisher;
import com.example.electionresults.integrationTests.domain.event.config.DummyElectionResultUpdateEventListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ElectionResultUpdateEventTest {

    @Autowired
    private ElectionResultUpdateEventPublisher electionResultUpdateEventPublisher;

    @Autowired
    private DummyElectionResultUpdateEventListener dummyEventListener;

    @Test
    public void testSearchAdvertise_whenCreateElectionScore() {

        dummyEventListener.initCounter();
        electionResultUpdateEventPublisher.publishUpdateEvent();
        dummyEventListener.getCounter();

        assertEquals(dummyEventListener.getCounter(), new Integer(1));
    }
}
