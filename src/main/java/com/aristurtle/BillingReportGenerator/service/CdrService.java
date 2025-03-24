package com.aristurtle.BillingReportGenerator.service;

import com.aristurtle.BillingReportGenerator.model.CDR;
import com.aristurtle.BillingReportGenerator.repository.CdrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class CdrService {
    private final CdrRepository cdrRepository;

    @Autowired
    public CdrService(CdrRepository cdrRepository) {
        this.cdrRepository = cdrRepository;
    }

    public List<CDR> getAllByMsisdn(String msisdn) {
        return cdrRepository.findAllByFromMsisdnOrToMsisdn(msisdn, msisdn);
    }

    public List<CDR> getAllByCallStartBetween(int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
        LocalDateTime end = start.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate().atTime(23, 59, 59);
        return cdrRepository.findAllByCallStartBetween(start, end);
    }

    public List<CDR> getAllByMsisdn(String msisdn, int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
        LocalDateTime end = start.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate().atTime(23, 59, 59);
        return cdrRepository.findCdrsByMsisdnAndDateRange(msisdn, start, end);
    }
}
