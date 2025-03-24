package com.aristurtle.BillingReportGenerator.controller;

import com.aristurtle.BillingReportGenerator.exception.InvalidRequestException;
import com.aristurtle.BillingReportGenerator.exception.UdrErrorResponse;
import com.aristurtle.BillingReportGenerator.model.UDR;
import com.aristurtle.BillingReportGenerator.service.UdrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/udr")
public class UdrController {
    private final UdrService udrService;

    @Autowired
    public UdrController(UdrService udrService) {
        this.udrService = udrService;
    }

    @GetMapping
    public ResponseEntity<List<UDR>> getForAll(@RequestParam(value = "month") int year,
                                               @RequestParam(value = "month") int month) {
        return ResponseEntity.ok(udrService.getForAll(year, month));

    }

    @GetMapping("/{msisdn}")
    public ResponseEntity<UDR> getForSubscriber(@PathVariable("msisdn") String msisdn,
                                                @RequestParam(value = "year", required = false) Optional<Integer> year,
                                                @RequestParam(value = "month", required = false) Optional<Integer> month) {
        if (year.isPresent() != month.isPresent())
            throw new InvalidRequestException("Parameters year and month must be present or not present together");
        if (year.isPresent()) {
            final UDR udr = udrService.getForMsisdn(msisdn, year.get(), month.get());
            return ResponseEntity.ok(udr);
        }
        return ResponseEntity.ok(udrService.getForMsisdn(msisdn));
    }

    @ExceptionHandler
    public ResponseEntity<UdrErrorResponse> handleException(InvalidRequestException e) {
        final UdrErrorResponse udrErrorResponse = new UdrErrorResponse(e.getLocalizedMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(udrErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
