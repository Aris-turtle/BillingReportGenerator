package com.aristurtle.BillingReportGenerator.service;

import com.aristurtle.BillingReportGenerator.model.CDR;
import com.aristurtle.BillingReportGenerator.model.UDR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.aristurtle.BillingReportGenerator.util.LocalDateTimeUtils.getEpochMilli;

@Service
public class UdrService {
    private final CdrService cdrService;

    @Autowired
    public UdrService(CdrService cdrService) {
        this.cdrService = cdrService;
    }

    public List<UDR> getForAllSubscribers(int year, int month) {
        final List<CDR> cdrList = cdrService.getAllByCallStartBetween(year, month);
        Map<String, List<CDR>> fromMsisdnCdrList = cdrList.stream()
                .collect(Collectors.groupingBy(CDR::getFromMsisdn));
        Map<String, List<CDR>> toMsisdnCdrList = cdrList.stream()
                .collect(Collectors.groupingBy(CDR::getToMsisdn));
        fromMsisdnCdrList.forEach((k, v) -> toMsisdnCdrList.merge(k, v, (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        }));

        return toMsisdnCdrList.entrySet().stream()
                .map(entry -> getUdr(entry.getKey(), entry.getValue()))
                .toList();
    }


    public UDR get(String msisdn) {
        List<CDR> allByMsisdn = cdrService.getAllByMsisdn(msisdn);
        return getUdr(msisdn, allByMsisdn);
    }

    public UDR get(String msisdn, int year, int month) {
        List<CDR> cdrList = cdrService.get(msisdn, year, month);
        return getUdr(msisdn, cdrList);
    }

    private static UDR getUdr(String msisdn, List<CDR> cdrListByMsisdn) {
        long incomingCallTotalTime = 0;
        long outcomingCallTotalTime = 0;
        for (CDR cdr : cdrListByMsisdn) {
            final long callDurationMillis = getEpochMilli(cdr.getCallEnd()) - getEpochMilli(cdr.getCallStart());
            if (cdr.getCallType().equals("01")) {
                outcomingCallTotalTime += callDurationMillis;
            } else
                incomingCallTotalTime += callDurationMillis;
        }

        return UDR.builder()
                .msisdn(msisdn)
                .incomingCall(new UDR.CallInfo(Long.toString(incomingCallTotalTime)))
                .outcomingCall(new UDR.CallInfo(Long.toString(outcomingCallTotalTime)))
                .build();
    }
}
