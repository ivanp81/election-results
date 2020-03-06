package com.example.electionresults.ingestion.transformer;

import com.example.electionresults.domain.event.ElectionResultUpdateEventPublisher;
import com.example.electionresults.domain.service.ElectionScoreService;
import com.example.electionresults.ingestion.transformer.xml.ConstituencyResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Component
public class XmlToObjectTransformer {

    Logger logger = LoggerFactory.getLogger(XmlToObjectTransformer.class);

    @ServiceActivator(inputChannel = "xmlToObjectProcessorChannel", outputChannel = "electionResultContentValidationProcessorInputChannel")
    public ConstituencyResults process(String xml) throws Exception {

        logger.info("Step 1 - Processing xml " + xml);

        try {

            Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(ConstituencyResults.class).createUnmarshaller();
            ConstituencyResults constituencyResults = (ConstituencyResults) jaxbUnmarshaller.unmarshal(new StringReader(xml));

            return constituencyResults;
        }
        catch(JAXBException  e){
            throw new Exception("Invalid XML file");
        }
    }
}