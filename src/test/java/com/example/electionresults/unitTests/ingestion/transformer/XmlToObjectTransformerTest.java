package com.example.electionresults.unitTests.ingestion.transformer;

import com.example.electionresults.ingestion.transformer.XmlToObjectTransformer;
import com.example.electionresults.ingestion.transformer.xml.ConstituencyResults;
import org.junit.Before;
import org.junit.Test;
import org.springframework.integration.file.transformer.FileToStringTransformer;

import static org.junit.Assert.assertNotNull;

public class XmlToObjectTransformerTest {

    private XmlToObjectTransformer xmlToObjectTransformer;

    private FileToStringTransformer fileToStringTransformer;

    @Before
    public void setUp() {
        xmlToObjectTransformer = new XmlToObjectTransformer();
    }

    @Test
    public void testUnmarshal_whenValidFile() throws Exception {

        String xml = "<constituencyResults></constituencyResults>";
        ConstituencyResults constituencyResults = xmlToObjectTransformer.process(xml);

        assertNotNull(constituencyResults);
    }

    @Test(expected = Exception.class)
    public void testUnmarshal_whenInvalidFile() throws Exception {

        String xml = "<constituencyResults></constituencyResults";
        xmlToObjectTransformer.process(xml);
    }
}
