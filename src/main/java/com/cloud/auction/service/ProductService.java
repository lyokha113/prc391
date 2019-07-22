package com.cloud.auction.service;

import com.cloud.auction.model.Product;
import com.cloud.auction.payload.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product getProduct(Integer id);
    List<Product> getProducts();
    List<Product> getActiveProducts();
    List<Product> getProductsByCategory(Integer id);
    Product createProduct(ProductRequest request);
    void updateProduct(Integer id, ProductRequest request);
}
