package com.aristurtle.BillingReportGenerator.repository;

import com.aristurtle.BillingReportGenerator.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    @Query(value = "SELECT msisdn FROM SUBSCRIBERS ORDER BY RAND() LIMIT 1", nativeQuery = true)
    String getRandomMsisdn();

    @Query(value = "SELECT msisdn FROM SUBSCRIBERS WHERE msisdn <> :exceptMsisdn  ORDER BY RAND() LIMIT 1", nativeQuery = true)
    String getRandomMsisdn(String exceptMsisdn);
}
