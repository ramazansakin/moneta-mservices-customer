package com.rsakin.customer.controller;

import com.rsakin.customer.dao.CustomerRepository;
import com.rsakin.customer.exception.CustomerNotFoundException;
import com.rsakin.customer.model.Customer;
import com.rsakin.customer.service.impl.CustomerServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebFluxTest(CustomerController.class)
@Import(CustomerServiceImpl.class)
class CustomerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void getById() {
        // stub
        Customer testCustomer = new Customer(1L, "1232141", "John", "Doe", 8000.0, "5123123123");
        //when
        BDDMockito.given(this.customerRepository.findById(1L))
                .willReturn(Mono.just(testCustomer));

        // then
        webTestClient.get()
                .uri("/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(Customer.class);
    }

    @Test
    void getAll() {
        // stub
        Customer testCustomer = new Customer(1L, "1232141", "John", "Doe", 8000.0, "5123123123");

        // when
        BDDMockito.given(this.customerRepository.findAll())
                .willReturn(Flux.just(testCustomer));

        // then
        webTestClient.get()
                .uri("/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(Customer.class);
    }

    @Test
    void create() {
        // stub
        Customer testCustomer = new Customer(1L, "1232141", "John", "Doe", 8000.0, "5123123123");

        BDDMockito.given(this.customerRepository.save(testCustomer))
                .willReturn(Mono.just(testCustomer));

        webTestClient.post()
                .uri("")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(Customer.class);
    }

    @Test
    void deleteById() {
        BDDMockito.given(this.customerRepository.delete(any()))
                .willThrow(CustomerNotFoundException.class);

        webTestClient.delete()
                .uri("/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void update() {
        // stub
        Customer testCustomer = new Customer(1L, "1232141", "John", "Doe", 8000.0, "5123123123");

        BDDMockito.given(this.customerRepository.save(any()))
                .willReturn(Mono.just(testCustomer));

        webTestClient.put()
                .uri("")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

}