package com.example.electionresults.ingestion;

import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingestion")
public class FileIngestionController {

    @Autowired
    private MessageChannel controlChannel;

    @GetMapping(value = "/startPoll")
    public String startPoll() {

        controlChannel.send(new GenericMessage("@fileReadingSource.start()"));
        return "started...";
    }

    @GetMapping(value = "/stopPoll")
    public String stopPoll() {

        controlChannel.send(new GenericMessage("@fileReadingSource.stop()"));
        return "stopping...";
    }
}
