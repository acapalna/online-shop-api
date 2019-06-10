package org.fasttrackit.onlineshopapi.web;

import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.CustomerService;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCustomerRequest;
import org.fasttrackit.onlineshopapi.transfer.customer.GetCustomerRequest;
import org.fasttrackit.onlineshopapi.transfer.customer.UpdateCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //endpoint
    @GetMapping
    public ResponseEntity<Page<Customer>> getCustomers(GetCustomerRequest request, Pageable pageable){
        Page<Customer> response = customerService.getCustomer(request, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid CreateCustomerRequest request){
        Customer customer = customerService.createCustomer(request);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCustomer(@PathVariable Long id) throws ResourceNotFoundException {
        Customer customer = customerService.getCustomer(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCustomer(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateCustomerRequest request) throws ResourceNotFoundException {
        customerService.updateCustomer(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
