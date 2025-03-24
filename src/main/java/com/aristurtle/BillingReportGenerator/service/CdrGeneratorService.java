package com.aristurtle.BillingReportGenerator.service;

import com.aristurtle.BillingReportGenerator.model.CDR;
import com.aristurtle.BillingReportGenerator.repository.CdrRepository;
import com.aristurtle.BillingReportGenerator.repository.SubscriberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static com.aristurtle.BillingReportGenerator.util.LocalDateTimeUtils.getEpochMilli;
import static com.aristurtle.BillingReportGenerator.util.LocalDateTimeUtils.getLocalDateTime;

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

    public List<CDR> generateForYear(LocalDateTime dateFrom,
                                     long avgCallDurationMs,
                                     long avgCallDurationStdDevMs,
                                     int recordAmountMin,
                                     int recordAmountMax) {
        LocalDateTime dateTo = dateFrom.plusYears(1);

        final int recordAmount = random.nextInt(recordAmountMax - recordAmountMin) + recordAmountMin;
        log.info("Generating {} CDR in interval from {} to {} ", recordAmount, dateFrom, dateTo);

        List<CDR> cdrList = new ArrayList<>();
        for (int i = 0; i < recordAmount; i++) {
            final String callType = random.nextBoolean() ? "01" : "02";

            final long callStart = random.nextLong(getEpochMilli(dateFrom), getEpochMilli(dateTo));
            final long callDuration = (long) random.nextGaussian(avgCallDurationMs, avgCallDurationStdDevMs);
            final long callEnd = callStart + callDuration;
            final LocalDateTime start = getLocalDateTime(callStart);
            final LocalDateTime end = getLocalDateTime(callEnd);

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
