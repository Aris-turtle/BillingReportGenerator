package com.aristurtle.BillingReportGenerator.repository;

import com.aristurtle.BillingReportGenerator.model.CDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CdrRepository extends JpaRepository<CDR, Long> {
    List<CDR> findAllByFromMsisdnOrToMsisdn(String fromMsisdn, String toMsisdn);
    @Query("SELECT c FROM CDR c WHERE " +
    "(c.fromMsisdn = :msisdn OR c.toMsisdn = :msisdn) " +
    "AND c.callStart BETWEEN :start AND :end")
    List<CDR> findCdrsByMsisdnAndDateRange(@Param("msisdn") String msisdn, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    @Query("SELECT c FROM CDR c WHERE " +
            "c.callStart BETWEEN :start AND :end")
    List<CDR> findAllByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
