package com.rsakin.customer.model;

import lombok.Value;

@Value
public class CreditStatusResponse {
    private Boolean status;
    private Long creditLimit;
}
