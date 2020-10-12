package com.rsakin.customer.service.impl;

import com.rsakin.customer.dao.CustomerRepository;
import com.rsakin.customer.model.CreditStatusResponse;
import com.rsakin.customer.model.Customer;
import com.rsakin.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    // Credit Service
    private WebClient webClient = WebClient.create("http://192.168.1.101:8091/credits");

    @Override
    public Mono<Customer> getById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Flux<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Mono<Customer> deleteById(Long id) {
        return customerRepository.findById(id)
                .flatMap(customer -> customerRepository.delete(customer)
                        .then(Mono.just(customer)));
    }

    @Override
    public Mono<Customer> update(Long id, Customer customer) {
        return customerRepository.findById(id)
                .flatMap(dbCustomer -> {
                    try {
                        BeanUtils.copyProperties(dbCustomer, customer);
                    } catch (Exception e) {
                        log.error("BeanUtils.copyProperties for customer has error : " + e.getMessage());
                        return Mono.just(null);
                    }
                    return customerRepository.save(dbCustomer);
                });
    }

    @Override
    public Mono<ResponseEntity<Map<String, String>>> getCustomerCreditAvailabilityStatus(Long id) {
        return customerRepository.findById(id)
                .flatMap(
                        customer -> webClient
                                .post()
                                .uri("/credit-status/" + id)
                                .bodyValue(customer)
                                .retrieve()
                                .bodyToMono(CreditStatusResponse.class)
                                .flatMap(creditStatusResponse -> {
                                    Boolean status = creditStatusResponse.getStatus();
                                    Map<String, String> resultMap = new HashMap<>();
                                    resultMap.put("Credit status",
                                            (status.equals(Boolean.TRUE) ? "Accepted" : "Denied"));
                                    resultMap.put("Cause",
                                            (status.equals(Boolean.TRUE) ? creditStatusResponse.getCreditLimit().toString() : "0"));

                                    return Mono.just(resultMap).map(ResponseEntity::ok);
                                })
                );

    }
}
