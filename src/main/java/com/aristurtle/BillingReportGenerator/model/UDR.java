package com.aristurtle.BillingReportGenerator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class UDR {
    private String msisdn;
    private CallInfo incomingCall;
    private CallInfo outcomingCall;

    @Setter
    @Getter
    @AllArgsConstructor
    public static class CallInfo {
        private String totalTime;
    }
}

