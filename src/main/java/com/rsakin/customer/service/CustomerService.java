package com.rsakin.customer.service;

import com.rsakin.customer.model.Customer;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface CustomerService {

    Mono<Customer> getById(Long id);

    Flux<Customer> getAll();

    Mono<Customer> create(Customer customer);

    Mono<Customer> deleteById(Long id);

    Mono<Customer> update(Long id, Customer customer);

    Mono<ResponseEntity<Map<String, String>>> getCustomerCreditAvailabilityStatus(Long id);

}
