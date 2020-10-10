package com.rsakin.customer.controller;

import com.rsakin.customer.model.CreditStatusResponse;
import com.rsakin.customer.model.Customer;
import com.rsakin.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Customer>> getById(@PathVariable Long id) {
        return customerService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public Flux<Customer> getAll() {
        return customerService.getAll();
    }

    @PostMapping
    public Mono<Customer> create(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Customer>> deleteById(@PathVariable Long id) {
        return customerService.deleteById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Mono<ResponseEntity<Customer>> update(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.update(id, customer)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/credit-availability")
    public Mono<ResponseEntity<Map<String, String>>> getCustomerCreditAvailabilityStatus(@PathVariable Long id) {
        // Credit Service
        WebClient webClient = WebClient.create("https://localhost:8091/credits");

        Mono<ResponseEntity<Customer>> byId = getById(id);

        Mono<CreditStatusResponse> creditStatusResponseMono =
                webClient
                        .post()
                        .uri("/credit-status/" + id)
                        .body(Objects.requireNonNull(byId.block().getBody()), Customer.class)
                        .retrieve()
                        .bodyToMono(CreditStatusResponse.class);

        Boolean status = creditStatusResponseMono.block().getStatus();
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("Credit status",
                (status.equals(Boolean.TRUE) ? "Accepted" : "Denied"));
        resultMap.put("Cause",
                (status.equals(Boolean.TRUE) ? creditStatusResponseMono.block().getCreditLimit().toString() : "0"));

        return Mono.just(resultMap).map(ResponseEntity::ok);
    }


}
