package org.fasttrackit.onlineshopapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.repository.CustomerRepository;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCustomerRequest;
import org.fasttrackit.onlineshopapi.transfer.customer.GetCustomerRequest;
import org.fasttrackit.onlineshopapi.transfer.customer.UpdateCustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CustomerService.class);

    //@Autowired //IoC (inversion of control) and dependency injection
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
    }

    public Customer createCustomer(CreateCustomerRequest request){
        LOGGER.info("Creating customer {}", request);

        Customer customer = objectMapper.convertValue(request, Customer.class);

//          same as above with object mapper
//        Customer customer= new Customer();
//        customer.setName(request.getName());
//        customer.setQuantity(request.getQuantity());
//        customer.setPrice(request.getPrice());
//        customer.setImagePath(request.getImagePath());

        return customerRepository.save(customer);
    }

    public Customer getCustomer(long id) throws ResourceNotFoundException {
        LOGGER.info("Retrieving customer {}", id);
        //using optional orElseTrow
        return customerRepository.findById(id)
                //using Lambda expressions
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + id + " does not exist"));
    }

    public Customer updateCustomer(long id, UpdateCustomerRequest request) throws ResourceNotFoundException {
        LOGGER.info("Updating customer {} with {}", id, request);

        Customer customer = getCustomer(id);
        BeanUtils.copyProperties(request, customer);

        return customerRepository.save(customer);
    }

    public void deleteCustomer(long id){
        LOGGER.info("Deleting customer {}", id);
        customerRepository.deleteById(id);
        LOGGER.info("Deleted customer {}", id);
    }

    public Page<Customer> getCustomer(GetCustomerRequest request, Pageable pageable) {
        LOGGER.info("Retrieving customer {}", request);

        if(request.getPartialId() != null){
            return customerRepository.findByIdContaining(request.getPartialId(), pageable);
        }
        else if(request.getPartialFirstName() != null){
            return customerRepository.findByFirstNameContaining(request.getPartialFirstName(), pageable);
        }
        else if(request.getPartialLastName() != null){
            return customerRepository.findByLastNameContaining(request.getPartialLastName(), pageable);
        }
        else if(request.getPartialPhoneNumber() != null){
            return customerRepository.findByPhoneNumberContaining(request.getPartialPhoneNumber(), pageable);
        }

        return customerRepository.findAll(pageable);
    }

}
