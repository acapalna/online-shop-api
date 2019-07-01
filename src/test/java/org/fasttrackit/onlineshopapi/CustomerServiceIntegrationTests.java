package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.service.CustomerService;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCustomerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceIntegrationTests {

    @Autowired
    CustomerService customerService;

    @Test
    public void testCreateCustomer_whenValidRequest_thenReturnCustomer(){
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
    }
}
