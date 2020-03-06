package com.example.electionresults.domain.repository;

import org.springframework.data.repository.CrudRepository;

public interface ElectionRepository extends CrudRepository<Election, Long> {

    Election findByActive(Boolean active);
}
