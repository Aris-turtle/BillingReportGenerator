//package com.aristurtle.BillingReportGenerator.service;
//
//import com.aristurtle.BillingReportGenerator.model.CDR;
//import com.aristurtle.BillingReportGenerator.repository.CdrRepository;
//import com.aristurtle.BillingReportGenerator.repository.SubscriberRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Calendar;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//public class CdrGeneratorServiceTest {
//    private final CdrGeneratorService cdrGeneratorService;
//    private final SubscriberRepository subscriberRepository;
//    private final CdrRepository cdrRepository;
//
//    @Autowired
//    public CdrGeneratorServiceTest(CdrGeneratorService cdrGeneratorService, SubscriberRepository subscriberRepository, CdrRepository cdrRepository) {
//        this.cdrGeneratorService = cdrGeneratorService;
//        this.subscriberRepository = subscriberRepository;
//        this.cdrRepository = cdrRepository;
//    }
//
//    @Test
//    public void testGetRandomMsisdn() {
//        String randomMsisdn = subscriberRepository.getRandomMsisdn();
//        assertNotNull(randomMsisdn);
//        String anotherRandomMsisdn = subscriberRepository.getRandomMsisdn(randomMsisdn);
//        assertNotNull(anotherRandomMsisdn);
//    }
//
//    @Test
//    public void generateForYear_returnListShouldEqualsDbList() {
//        List<CDR> cdrList = cdrGeneratorService.generateForYear(Calendar.getInstance(), 60000, 15000, 50, 150);
//        List<CDR> cdrFromDb = cdrRepository.findAll();
//        assertEquals(cdrList.size(), cdrFromDb.size());
//        assertEquals(cdrList, cdrFromDb);
//    }
//}
