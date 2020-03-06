package com.example.electionresults.ingestion.transformer.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConstituencyResults {

    private ConstituencyResult constituencyResult;

    public ConstituencyResults() {
    }

    public ConstituencyResults(ConstituencyResult constituencyResult) {
        this.constituencyResult = constituencyResult;
    }

    @XmlElement
    public ConstituencyResult getConstituencyResult() {
        return constituencyResult;
    }

    public void setConstituencyResult(ConstituencyResult constituencyResult) {
        this.constituencyResult = constituencyResult;
    }
}
