package com.rsakin.customer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private Long id;
    private String tckn;
    private String name;
    private String lastname;
    private Double salary;
    private String phone;

}
