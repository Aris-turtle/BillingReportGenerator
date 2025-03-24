package com.aristurtle.BillingReportGenerator.service;

import com.aristurtle.BillingReportGenerator.model.CDR;
import com.aristurtle.BillingReportGenerator.repository.CdrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class CdrService {
    private final CdrRepository cdrRepository;

    @Autowired
    public CdrService(CdrRepository cdrRepository) {
        this.cdrRepository = cdrRepository;
    }

    public List<CDR> getAllByMsisdn(String msisdn) {
        return cdrRepository.findAllByMsisdn(msisdn);
    }

    public List<CDR> getAllByCallStartBetween(int year, int month) {
        Calendar start = Calendar.getInstance();
        start.set(year, month, 1, 0, 0, 0);
        Calendar end = Calendar.getInstance();
        end.set(year, month, start.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return cdrRepository.findAllByCallStartBetween(start, end);
    }

    public List<CDR> getAllByMsisdnAndCallStartBetween(String msisdn, int year, int month) {
        Calendar start = Calendar.getInstance();
        start.set(year, month, 1, 0, 0, 0);
        Calendar end = Calendar.getInstance();
        end.set(year, month, start.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return cdrRepository.findAllByMsisdnAndCallStartBetween(msisdn, start, end);
    }
}
