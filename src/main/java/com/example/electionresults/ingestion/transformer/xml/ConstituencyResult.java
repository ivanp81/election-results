package com.example.electionresults.ingestion.transformer.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConstituencyResult {

    private Long seqNo;
    private Long consituencyId;
    private String constituencyName;
    private Results results;

    public ConstituencyResult() {
    }

    public ConstituencyResult(Long seqNo, Long consituencyId, String constituencyName, Results results) {
        this.seqNo = seqNo;
        this.consituencyId = consituencyId;
        this.constituencyName = constituencyName;
        this.results = results;
    }

    @XmlAttribute
    public Long getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Long seqNo) {
        this.seqNo = seqNo;
    }

    @XmlElement
    public Long getConsituencyId() {
        return consituencyId;
    }

    public void setConsituencyId(Long consituencyId) {
        this.consituencyId = consituencyId;
    }

    @XmlElement
    public String getConstituencyName() {
        return constituencyName;
    }

    public void setConstituencyName(String constituencyName) {
        this.constituencyName = constituencyName;
    }

    @XmlElement
    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }
}
