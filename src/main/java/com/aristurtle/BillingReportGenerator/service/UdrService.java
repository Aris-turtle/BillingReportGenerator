package com.aristurtle.BillingReportGenerator.service;

import com.aristurtle.BillingReportGenerator.model.CDR;
import com.aristurtle.BillingReportGenerator.model.UDR;
import com.aristurtle.BillingReportGenerator.repository.CdrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class UdrService {
    private final CdrService cdrService;
    private final CdrRepository cdrRepository;

    @Autowired
    public UdrService(CdrService cdrService, CdrRepository cdrRepository) {
        this.cdrService = cdrService;
        this.cdrRepository = cdrRepository;
    }

//    public List<UDR> get(int year, int month) {
//        List<CDR> cdrListForMonth = cdrService.getAllByCallStartBetween(year, month);
//        cdrListForMonth.stream().
//                map(e -> get)
//    }


    public UDR get(String msisdn, Calendar month) {
        List<CDR> allByMsisdn = cdrService.getAllByMsisdn(msisdn);
        List<CDR> filteredList = allByMsisdn.stream()
                .filter(cdr ->
                        cdr.getCallStart().get(Calendar.MONTH) == month.get(Calendar.MONTH))
                .toList();
        return getUdr(msisdn, filteredList);
    }

    public UDR get(String msisdn) {
        List<CDR> allByMsisdn = cdrService.getAllByMsisdn(msisdn);
        return getUdr(msisdn, allByMsisdn);
    }

    private static UDR getUdr(String msisdn, List<CDR> cdrListByMsisdn) {
        long incomingCallTotalTime = 0;
        long outcomingCallTotalTime = 0;
        for (CDR cdr : cdrListByMsisdn) {
            if (cdr.getCallType().equals("01"))
                outcomingCallTotalTime += (cdr.getCallEnd().getTimeInMillis() - cdr.getCallStart().getTimeInMillis());
            else
                incomingCallTotalTime += (cdr.getCallEnd().getTimeInMillis() - cdr.getCallStart().getTimeInMillis());
        }

        return UDR.builder()
                .msisdn(msisdn)
                .incomingCall(new UDR.CallInfo(Long.toString(incomingCallTotalTime)))
                .outcomingCall(new UDR.CallInfo(Long.toString(outcomingCallTotalTime)))
                .build();
    }
}
