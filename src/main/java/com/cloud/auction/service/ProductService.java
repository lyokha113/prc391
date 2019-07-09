package com.cloud.auction.service;

import com.cloud.auction.model.Product;
import com.cloud.auction.payload.ProductRequest;

import java.util.List;

public interface ProductService {
    Product getProduct(Integer id);
    List<Product> getProducts();
    List<Product> getActiveProducts();
    List<Product> getProductsByCategory(Integer id);
    void updateProduct(Integer id, ProductRequest request);
    void createProduct(ProductRequest request);
}
