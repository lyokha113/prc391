package com.cloud.auction.service.impl;

import com.cloud.auction.exception.AppException;
import com.cloud.auction.model.Product;
import com.cloud.auction.payload.ProductRequest;
import com.cloud.auction.repository.ProductRepository;
import com.cloud.auction.service.FirebaseService;
import com.cloud.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FirebaseService firebaseService;

    @Override
    public Product getProduct(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getActiveProducts() {
        return productRepository.getAllByActiveTrue();
    }

    @Override
    public List<Product> getProductsByCategory(Integer id) {
        return productRepository.getAllByActiveTrueAndCategory_Id(id);
    }

    @Override
    public void updateProduct(Integer id, ProductRequest request) {
        Product product = getProduct(id);
        if (product != null) {
            product.setDescription(request.getDescription());
            product.setName(request.getName());
            product.setPrice(request.getPrice());
            product.setActive(request.isActive());
        } else {
            throw new AppException("product not found");
        }
    }

    @Override
    public void createProduct(ProductRequest request) {
        Product product = new Product();
//        product.setDescription(request.getDescription());
//        product.setName(request.getName());
//        product.setPrice(request.getPrice());
//        product.setActive(request.isActive());
//        productRepository.save(product);
        firebaseService.uploadImages("/p" + request.getId(), request.getImages());
    }
}
