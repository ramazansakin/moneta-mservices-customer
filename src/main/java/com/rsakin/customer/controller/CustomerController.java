package com.rsakin.customer.controller;

import com.rsakin.customer.model.Customer;
import com.rsakin.customer.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/customer")
@Api(value = "Customer Controller REST Endpoints")
@ApiResponses(
        value = {
                @ApiResponse(code = 400, message = "Bad Request"),
                @ApiResponse(code = 200, message = "Successful"),
                @ApiResponse(code = 404, message = "Not Found")
        }
)
public class CustomerController {

    private final CustomerService customerService;

    @ApiOperation(value = "Returns a customer wrapped via Mono")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Customer>> getById(@PathVariable Long id) {
        return customerService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Returns all customers wrapped via Flux")
    @GetMapping("/all")
    public Flux<Customer> getAll() {
        return customerService.getAll();
    }

    @ApiOperation(value = "Create new customer wrapped via Mono")
    @PostMapping
    public Mono<Customer> create(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @ApiOperation(value = "Delete a customer wrapped via Mono")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Customer>> deleteById(@PathVariable Long id) {
        return customerService.deleteById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Update a customer wrapped via Mono")
    @PutMapping
    public Mono<ResponseEntity<Customer>> update(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.update(id, customer)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Query a customer's credit availability status on Credit Service")
    @GetMapping("/{id}/credit-availability")
    public Mono<ResponseEntity<Map<String, String>>> getCustomerCreditAvailabilityStatus(@PathVariable Long id) {
        return customerService.getCustomerCreditAvailabilityStatus(id)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
