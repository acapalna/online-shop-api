package org.fasttrackit.onlineshopapi.steps;

import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.service.CustomerService;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@Component
public class CustomerSteps {

    @Autowired
    CustomerService customerService;

    public Customer createCustomer(){
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFirstName("Dorel");
        request.setLastName("Tarnacop");
        request.setPhoneNumber("0938120937812093");
        request.setAddress("dasdasdasd adsa fdasf adsg fd");

        Customer customer = customerService.createCustomer(request);

        assertThat(customer, notNullValue());
        assertThat(customer.getId(), greaterThan(0L));
        assertThat(customer.getFirstName(), is(request.getFirstName()));
        assertThat(customer.getLastName(), is(request.getLastName()));
        assertThat(customer.getPhoneNumber(), is(request.getPhoneNumber()));
        assertThat(customer.getAddress(), is(request.getAddress()));

        return customer;
    }
}
