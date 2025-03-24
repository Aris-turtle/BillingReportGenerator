package com.aristurtle.BillingReportGenerator.repository;

import com.aristurtle.BillingReportGenerator.model.CDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CdrRepository extends JpaRepository<CDR, Long> {
    List<CDR> findAllByFromMsisdnOrToMsisdn(String fromMsisdn, String toMsisdn);
    List<CDR> findAllByFromMsisdnOrToMsisdnAndCallStartBetween(String fromMsisdn, String toMsisdn, LocalDateTime start, LocalDateTime end);
    List<CDR> findAllByCallStartBetween(LocalDateTime start, LocalDateTime end);
}
