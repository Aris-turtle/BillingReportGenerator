package com.aristurtle.BillingReportGenerator.controller;

import com.aristurtle.BillingReportGenerator.model.CDR;
import com.aristurtle.BillingReportGenerator.service.CdrGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cdr")
public class CdrController {
    private final CdrGeneratorService cdrGeneratorService;

    @Autowired
    public CdrController(CdrGeneratorService cdrGeneratorService) {
        this.cdrGeneratorService = cdrGeneratorService;
    }

    @PostMapping
    public ResponseEntity<List<CDR>> generateCdrForYear(@RequestParam("dateFrom") String dateFrom,
                                                        @RequestParam("avgCallDurationMs") long avgCallDurationMs,
                                                        @RequestParam("avgCallDurationStdDevMs") long avgCallDurationStdDevMs,
                                                        @RequestParam("recordAmountMin") int recordAmountMin,
                                                        @RequestParam("recordAmountMax") int recordAmountMax) {
        final LocalDateTime startTime = LocalDateTime.parse(dateFrom);
        final List<CDR> cdrList = cdrGeneratorService.generateForYear(startTime,
                avgCallDurationMs,
                avgCallDurationStdDevMs,
                recordAmountMin,
                recordAmountMax);
        return ResponseEntity.ok(cdrList);
    }
}
