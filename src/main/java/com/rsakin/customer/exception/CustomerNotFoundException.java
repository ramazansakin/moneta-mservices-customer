package com.rsakin.customer.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("There is no such customer with id [ " + id + " ]");
    }
}
