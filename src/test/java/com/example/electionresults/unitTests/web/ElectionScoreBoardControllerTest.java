package com.example.electionresults.unitTests.web;

import com.example.electionresults.domain.service.ElectionScoreBoardService;
import com.example.electionresults.web.ElectionScoreBoardController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ElectionScoreBoardControllerTest {

    private ElectionScoreBoardController electionScoreBoardController;

    @Mock
    private ElectionScoreBoardService electionScoreBoardService;

    @Before
    public void setUp() {
        initMocks(this);
        electionScoreBoardController = new ElectionScoreBoardController(electionScoreBoardService);
    }

    @Test
    public void testGetElectionScore() {

        electionScoreBoardController.getElectionScore();

        verify(electionScoreBoardService, times(1)).liveScore();
    }
}

