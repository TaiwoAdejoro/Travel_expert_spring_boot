package com.project.travelExperts.controller;

import com.project.travelExperts.data.dto.request.CreateProductRequest;
import com.project.travelExperts.data.dto.request.UpdateProductRequest;
import com.project.travelExperts.data.dto.response.BaseResponse;
import com.project.travelExperts.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        BaseResponse<?> products = productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getAllProduct(@PathVariable @Valid @NotNull(message = "Product Id is required") long productId) {
        BaseResponse<?> products = productService.findProductById(productId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody @Valid  CreateProductRequest createProductRequest) {
        BaseResponse<?> response = productService.createProduct(createProductRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable @Valid @NotNull(message = "Product Id is required") long productId, @RequestBody @Valid UpdateProductRequest createProductRequest) {
        BaseResponse<?> response = productService.updateProduct(productId, createProductRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable @Valid @NotNull(message = "Product Id is required") long productId) {
        BaseResponse<?> response = productService.deleteProductByProductId(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
