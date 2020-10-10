package com.rsakin.customer.service.impl;

import com.rsakin.customer.dao.CustomerRepository;
import com.rsakin.customer.model.Customer;
import com.rsakin.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

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
}
