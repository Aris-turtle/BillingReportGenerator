package com.aristurtle.BillingReportGenerator.repository;

import com.aristurtle.BillingReportGenerator.model.CDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface CdrRepository extends JpaRepository<CDR, Long> {
    List<CDR> findAllByMsisdn(String msisdn);
    List<CDR> findAllByMsisdnAndCallStartBetween(String msisdn, Calendar start, Calendar end);
    List<CDR> findAllByCallStartBetween(Calendar start, Calendar end);
}
