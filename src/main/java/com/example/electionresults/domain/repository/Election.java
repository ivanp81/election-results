package com.example.electionresults.domain.repository;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "ELECTION")
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigInteger year;

    @Column(name="TOT_SEATS")
    private BigInteger totalSeats;

    private Boolean active;

    public Election() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getYear() {
        return year;
    }

    public void setYear(BigInteger year) {
        this.year = year;
    }

    public BigInteger getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(BigInteger totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
