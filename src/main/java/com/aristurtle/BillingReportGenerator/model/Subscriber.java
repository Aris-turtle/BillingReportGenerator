package com.aristurtle.BillingReportGenerator.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SUBSCRIBERS")
@Setter
@Getter
@ToString
public class Subscriber {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "msisdn")
    private String msisdn;
}
