package com.example.electionresults.ingestion.transformer.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Results {

    private List<Result> result;

    public Results() {
    }

    public Results(List<Result> result) {
        this.result = result;
    }

    @XmlElement
    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}
