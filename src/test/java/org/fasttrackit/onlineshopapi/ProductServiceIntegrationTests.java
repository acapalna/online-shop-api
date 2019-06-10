package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.UpdateProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {
    @Autowired
    private ProductService productService;

	@Test
	public void testCreateProduct_whenValidRequest_thenReturnCreatedProduct() {
        createProduct("Nivea", 99, 12);
    }

    @Test(expected = TransactionSystemException.class)
    public void testCreateProduct_whenMissingMandatoryProperties_thenThrowException(){
        CreateProductRequest request = new CreateProductRequest();

        productService.createProduct(request);
    }

    @Test
    public void testGetProduct_whenExistingId_ThenReturnProduct() throws ResourceNotFoundException {
        Product createdProduct = createProduct("Fasole", 22, 3);

        Product product = productService.getProduct(createdProduct.getId());

        assertThat(product, notNullValue());
        assertThat(product.getId(), is(createdProduct.getId()));
        assertThat(product.getName(), is(createdProduct.getName()));
        assertThat(product.getPrice(), is(createdProduct.getPrice()));
        assertThat(product.getQuantity(), is(createdProduct.getQuantity()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void  testGetProduct_whenNonExistingId_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
        productService.getProduct(9999L);
    }

    @Test
    public void testUpdateProduct_whenValidRequest_thenReturnUpdatedProduct() throws ResourceNotFoundException {
        Product createdProduct = createProduct("Zahar", 100, 3);

        UpdateProductRequest request = new UpdateProductRequest();
        request.setName(createdProduct.getName() + "_Updated");
        request.setPrice(createdProduct.getPrice() + 10);
        request.setQuantity(createdProduct.getQuantity() + 10);

        Product updatedProduct = productService.updateProduct(createdProduct.getId(), request);

        assertThat(updatedProduct, notNullValue());
        assertThat(updatedProduct.getId(), is(createdProduct.getId()));
        assertThat(updatedProduct.getQuantity(), not(is(createdProduct.getQuantity())));
        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));
        assertThat(updatedProduct.getPrice(), not(is(createdProduct.getPrice())));
        assertThat(updatedProduct.getPrice(), is(request.getPrice()));
        assertThat(updatedProduct.getName(), not(is(createdProduct.getName())));
        assertThat(updatedProduct.getName(), is(request.getName()));


        Product product = productService.getProduct(createdProduct.getId());
        assertThat(product, notNullValue());
        assertThat(product.getName(), not(is(createdProduct.getName())));
        assertThat(product.getName(), is(updatedProduct.getName()));
        assertThat(product.getPrice(), not(is(createdProduct.getPrice())));
        assertThat(product.getPrice(), is(updatedProduct.getPrice()));
        assertThat(product.getQuantity(), not(is(createdProduct.getQuantity())));
        assertThat(product.getQuantity(), is(updatedProduct.getQuantity()));
    }



    private Product createProduct(String name, int qty, double price) {
        CreateProductRequest request = new CreateProductRequest();
        request.setName(name);
        request.setQuantity(qty);
        request.setPrice(price);

        Product createdProduct = productService.createProduct(request);

        assertThat(createdProduct, notNullValue());
        assertThat(createdProduct.getId(), greaterThan(0L));
        assertThat(createdProduct.getName(), is(request.getName()));
        assertThat(createdProduct.getPrice(), is(request.getPrice()));
        assertThat(createdProduct.getQuantity(), is(request.getQuantity()));

        return createdProduct;
    }
}
