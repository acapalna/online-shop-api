package org.fasttrackit.onlineshopapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.repository.ProductRepository;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.GetProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.ProductResponse;
import org.fasttrackit.onlineshopapi.transfer.product.UpdateProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ProductService.class);

    //@Autowired //IoC (inversion of control) and dependency injection
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public Product createProduct(CreateProductRequest request){
        LOGGER.info("Creating product {}", request);

        Product product = objectMapper.convertValue(request, Product.class);

//          same as above with object mapper
//        Product product= new Product();
//        product.setName(request.getName());
//        product.setQuantity(request.getQuantity());
//        product.setPrice(request.getPrice());
//        product.setImagePath(request.getImagePath());

        return productRepository.save(product);
    }

    public Product getProduct(long id) throws ResourceNotFoundException {
        LOGGER.info("Retrieving product {}", id);
        //using optional orElseTrow
        return productRepository.findById(id)
                //using Lambda expressions
                .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " does not exist"));
    }

    public Product updateProduct(long id, UpdateProductRequest request) throws ResourceNotFoundException {
        LOGGER.info("Updating product {} with {}", id, request);

        Product product = getProduct(id);
        BeanUtils.copyProperties(request, product);

        return productRepository.save(product);
    }

    public void deleteProduct(long id){
        LOGGER.info("Deleting product {}", id);
        productRepository.deleteById(id);
        LOGGER.info("Deleted product {}", id);
    }

    public Page<ProductResponse> getProducts(GetProductRequest request, Pageable pageable) {
        LOGGER.info("Retrieving products {}", request);

        Page<Product> products;

        if (request.getPartialName() != null && request.getMinQuantity() != null) {
            products = productRepository.findByNameContainingAndQuantityGreaterThanEqual(request.getPartialName(), request.getMinQuantity(), pageable);
        }
        else if (request.getPartialName() != null) {
            products = productRepository.findByNameContaining(request.getPartialName(), pageable);
        }
        else if (request.getPartialName() != null && request.getMinPrice() != null && request.getMaxPrice() != null) {
            products =  productRepository.findByNameContainingAndPriceBetween(request.getPartialName(), request.getMinPrice(), request.getMaxPrice(), pageable);
        }
        else if (request.getMinPrice() != null && request.getMaxPrice() != null) {
            products =  productRepository.findAllByPriceBetween(request.getMinPrice(), request.getMaxPrice(), pageable);
        }
        else if (request.getMinPrice() != null && request.getMaxPrice() == null) {
            products =  productRepository.findAllByPriceGreaterThan(request.getMinPrice(), pageable);
        }
        else if (request.getMaxPrice() != null && request.getMinPrice() == null) {
            products =  productRepository.findAllByPriceLessThan(request.getMaxPrice(), pageable);
        }else {
            products = productRepository.findAll(pageable);
        }

        List<ProductResponse> productResponses = new ArrayList<>();

        products.getContent().forEach(product -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setPrice(product.getPrice());
            productResponse.setPriceBig(product.getPriceBig());
            productResponse.setImagePath(product.getImagePath());

            productResponses.add(productResponse);
        });

        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }

}
