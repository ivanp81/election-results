package com.example.electionresults.ingestion.transformer.xml;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Result {

    private BigInteger votes;
    private String partyCode;
    private BigDecimal share;

    public Result() {
    }

    public Result(BigInteger votes, String partyCode, BigDecimal share) {
        this.votes = votes;
        this.partyCode = partyCode.trim();
        this.share = share;
    }

    @XmlElement
    public BigInteger getVotes() {
        return votes;
    }

    public void setVotes(BigInteger votes) {
        this.votes = votes;
    }

    @XmlElement
    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode.trim();
    }

    @XmlElement
    public BigDecimal getShare() {
        return share;
    }

    public void setShare(BigDecimal share) {
        this.share = share;
    }
}
