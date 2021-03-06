package org.fasttrackit.onlineshopapi.web;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.GetProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.ProductResponse;
import org.fasttrackit.onlineshopapi.transfer.product.UpdateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //endpoint
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(GetProductRequest request, Pageable pageable){
        Page<ProductResponse> response = productService.getProducts(request, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid CreateProductRequest request){
        Product product = productService.createProduct(request);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProduct(@PathVariable Long id) throws ResourceNotFoundException {
        Product product = productService.getProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateProductRequest request) throws ResourceNotFoundException {
        productService.updateProduct(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
