package com.project.travelExperts.service.impl;

import com.project.travelExperts.data.dto.request.CreateProductRequest;
import com.project.travelExperts.data.dto.request.UpdateProductRequest;
import com.project.travelExperts.data.dto.response.BaseResponse;
import com.project.travelExperts.data.dto.response.CreateProductResponse;
import com.project.travelExperts.data.dto.response.ProductResponseDto;
import com.project.travelExperts.data.model.Product;
import com.project.travelExperts.data.repository.ProductRepository;
import com.project.travelExperts.exception.ProductServiceException;
import com.project.travelExperts.service.ProductService;
import com.project.travelExperts.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> saveProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public CreateProductResponse createProduct(CreateProductRequest createProductRequest) {
        Product product = new Product();
        product.setProductName(createProductRequest.getProductName());
        product.setProductCost(createProductRequest.getProductCost());
        product.setAvailable(true);
        product.setCreatedBy(getCurrentUserEmail());
        product = saveProduct(product);
        return new CreateProductResponse(201,true,"Product created successfully", Util.mapToProductResponse(product));
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUsername();
        }

        return null;
    }

    @Override
    public void deleteProduct(long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public BaseResponse<?> deleteProductByProductId(long productId) {
        deleteProduct(productId);
        return new BaseResponse<>(200, true, "Product deleted successfully", null);
    }

    @Override
    public void deleteProduct(Product product) {
    productRepository.delete(product);
    }

    @Override
    public Product findById(long productId) {
        return productRepository.findById(productId).orElseThrow(()-> new ProductServiceException("Product not found"));
    }

    @Override
    public BaseResponse<ProductResponseDto> findProductById(long productId) {
        Product product = findById(productId);
        ProductResponseDto productResponseDto =  Util.mapToProductResponse(product);
        return new BaseResponse<>(200, true, "Product retrieved successfully", productResponseDto);
    }

    @Override
    public BaseResponse<List<ProductResponseDto>> findAllProducts() {
        List<Product> products =  productRepository.findAll();
        List<ProductResponseDto> productResponseDtos = products.stream().map(Util::mapToProductResponse).toList();
        return new BaseResponse<>(200, true, "Products retrieved successfully", productResponseDtos);
    }

    @Override
    public BaseResponse<?> updateProduct(long productId, UpdateProductRequest updateProductRequest) {
        validateUpdateProductRequest(updateProductRequest);
        Product product = findById(productId);
        if (null != updateProductRequest.getProductName() && !updateProductRequest.getProductName().isEmpty()) {
            product.setProductName(updateProductRequest.getProductName());
        }
        if (null != updateProductRequest.getProductCost() && updateProductRequest.getProductCost().doubleValue() > 0) {
            product.setProductCost(updateProductRequest.getProductCost());
        }
        product = saveProduct(product);
        return new BaseResponse<>(200, true, "Product updated successfully", Util.mapToProductResponse(product));
    }

    private void validateUpdateProductRequest(UpdateProductRequest updateProductRequest){
        if ((null == updateProductRequest.getProductName() || updateProductRequest.getProductName().isEmpty()) &&(null == updateProductRequest.getProductCost() || updateProductRequest.getProductCost().doubleValue() <= 0)) {
            throw new ProductServiceException("Product name or cost is required");
        }
    }
}
