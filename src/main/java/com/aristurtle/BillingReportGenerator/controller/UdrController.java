package com.aristurtle.BillingReportGenerator.controller;

import com.aristurtle.BillingReportGenerator.service.CdrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/udr")
public class UdrController {
    private final CdrService cdrService;

    @Autowired
    public UdrController(CdrService cdrService) {
        this.cdrService = cdrService;
    }

//    @GetMapping()
//    public ResponseEntity<UdrDto> getForAll(@RequestParam(value = "month") String month) {
//        cdrService.get
//
//    }

//    @GetMapping("/{msisdn}")
//    public ResponseEntity<UDR> getForAll(@PathVariable("msidn") String msisdn,
//                                         @RequestParam(value = "month", required = false) String month) {
//
//    }

}
