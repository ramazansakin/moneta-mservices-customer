package com.rsakin.customer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    private Long id;
    private String tckn;
    private String name;
    private String lastname;
    private Double salary;
    private String phone;

}
