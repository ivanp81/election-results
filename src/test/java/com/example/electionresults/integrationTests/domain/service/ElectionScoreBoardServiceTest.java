package com.example.electionresults.integrationTests.domain.service;

import com.example.electionresults.domain.model.ElectionScoreBoard;
import com.example.electionresults.domain.repository.ElectionScoreRepository;
import com.example.electionresults.domain.service.ElectionScoreBoardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ElectionScoreBoardServiceTest {

    @Autowired
    private ElectionScoreBoardService electionScoreBoardService;

    @Autowired
    private ElectionScoreRepository electionScoreRepository;

    @Test
    public void testLiveScore() {

        ElectionScoreBoard electionScoreBoard = electionScoreBoardService.liveScore();
        assertNotNull(electionScoreBoard);
    }
}

