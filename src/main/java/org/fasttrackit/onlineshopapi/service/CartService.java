package org.fasttrackit.onlineshopapi.service;

import org.fasttrackit.onlineshopapi.domain.Cart;
import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.repository.CartRepository;
import org.fasttrackit.onlineshopapi.transfer.cart.AddProductToCartRequest;
import org.fasttrackit.onlineshopapi.transfer.cart.CartResponse;
import org.fasttrackit.onlineshopapi.transfer.cart.RemoveProductFromCartRequest;
import org.fasttrackit.onlineshopapi.transfer.customer.CustomerResponse;
import org.fasttrackit.onlineshopapi.transfer.product.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final CartResponse cartResponse;

    @Autowired
    public CartService(CartRepository cartRepository, CustomerService customerService, ProductService productService, CartResponse cartResponse) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
        this.productService = productService;
        this.cartResponse = cartResponse;
    }

    @Transactional
    public void addProductToCart(AddProductToCartRequest request) throws ResourceNotFoundException {
        LOGGER.info("Saving cart {}", request);

        Customer customer = customerService.getCustomer(request.getCustomerId());
        Cart cart = new Cart();
        cart.setCustomer(customer);

        Product product = productService.getProduct(request.getProductId());
        cart.addProduct(product);

        cartRepository.save(cart);
    }

    @Transactional
    public void removeProductFromCart(RemoveProductFromCartRequest request) throws ResourceNotFoundException {
        LOGGER.info("Removing product from cart {}", request);

        Customer customer = customerService.getCustomer(request.getCustomerId());
        Cart cart = new Cart();
        cart.setCustomer(customer);

        Product product = productService.getProduct(request.getProductId());
        cart.removeProduct(product);

        cartRepository.save(cart);
    }

    @Transactional
    public CartResponse getCart(Long customerId) throws ResourceNotFoundException {
        Cart cart = cartRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart " + customerId + " does not exist"));

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(cart.getCustomer().getId());
        customerResponse.setFirstName(cart.getCustomer().getFirstName());
        customerResponse.setLastName(cart.getCustomer().getLastName());


        CartResponse response = new CartResponse();
        response.setId(cart.getId());
        response.setCustomer(customerResponse);

        //ProductResponse productResponse = new ProductResponse();
        cart.getProducts().forEach(product -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setPrice(product.getPrice());
            productResponse.setPriceBig(product.getPriceBig());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setImagePath(product.getImagePath());

            cartResponse.getProducts().add(productResponse);

        });





        return response;
    }
}
