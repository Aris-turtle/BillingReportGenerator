package com.aristurtle.BillingReportGenerator.service;

import com.aristurtle.BillingReportGenerator.model.CDR;
import com.aristurtle.BillingReportGenerator.repository.CdrRepository;
import com.aristurtle.BillingReportGenerator.repository.SubscriberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CdrGeneratorService {
    private final SubscriberRepository subscriberRepository;
    private final CdrRepository cdrRepository;
    private final Random random = new Random();

    @Autowired
    public CdrGeneratorService(SubscriberRepository subscriberRepository, CdrRepository cdrRepository) {
        this.subscriberRepository = subscriberRepository;
        this.cdrRepository = cdrRepository;
    }

    public List<CDR> generateForYear(Calendar dateFrom,
                                     long avgCallDurationMs,
                                     long avgCallDurationStdDevMs,
                                     int recordAmountMin,
                                     int recordAmountMax) {
        final Calendar dateTo = ((Calendar) dateFrom.clone());
        dateTo.add(Calendar.YEAR, 1);

        final int recordAmount = random.nextInt(recordAmountMin + recordAmountMax) + recordAmountMin;
        log.info("Generating {} CDR in interval from {} to {} ", recordAmount, dateFrom.getTime(), dateTo.getTime());

        List<CDR> cdrList = new ArrayList<>();
        for (int i = 0; i < recordAmount; i++) {
            final String callType = random.nextBoolean() ? "01" : "02";
            final long callStart = random.nextLong(dateFrom.getTimeInMillis(), dateTo.getTimeInMillis());
            final long callDuration = (long) random.nextGaussian(avgCallDurationMs, avgCallDurationStdDevMs);
            final long callEnd = callStart + callDuration;
            final Calendar start = new GregorianCalendar();
            final Calendar end = new GregorianCalendar();
            start.setTimeInMillis(callStart);
            end.setTimeInMillis(callEnd);
            final String fromMsisdn = subscriberRepository.getRandomMsisdn();
            final String toMsisdn = subscriberRepository.getRandomMsisdn(fromMsisdn);

            final CDR cdr = CDR.builder()
                    .callType(callType)
                    .fromMsisdn(fromMsisdn)
                    .toMsisdn(toMsisdn)
                    .callStart(start)
                    .callEnd(end)
                    .build();
            cdrList.add(cdr);
        }

        cdrList.sort(Comparator.comparing(CDR::getCallStart));
        cdrRepository.saveAll(cdrList);
        return cdrList;
    }
}
