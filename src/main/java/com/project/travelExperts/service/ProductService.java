package com.project.travelExperts.service;

import com.project.travelExperts.data.dto.request.CreateProductRequest;
import com.project.travelExperts.data.dto.request.UpdateProductRequest;
import com.project.travelExperts.data.dto.response.BaseResponse;
import com.project.travelExperts.data.dto.response.CreateProductResponse;
import com.project.travelExperts.data.dto.response.ProductResponseDto;
import com.project.travelExperts.data.model.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);
    List<Product> saveProducts(List<Product> products);

    CreateProductResponse createProduct(CreateProductRequest createProductRequest);

    void deleteProduct(long productId);
    BaseResponse<?> deleteProductByProductId(long productId);

    void deleteProduct(Product product);

    Product findById(long productId);
    BaseResponse<ProductResponseDto> findProductById(long productId);

    BaseResponse<List<ProductResponseDto>>findAllProducts();

    BaseResponse<?> updateProduct(long productId, UpdateProductRequest updateProductRequest);
}
