package com.cloud.auction.service.impl;


import com.cloud.auction.model.Product;
import com.cloud.auction.model.ProductImage;
import com.cloud.auction.repository.ProductImageRepository;
import com.cloud.auction.repository.ProductRepository;
import com.cloud.auction.service.ProductImageService;
import com.cloud.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    public ProductImage createImage(Integer id, String file) {
        Product product = productService.getProduct(id);
        if (product != null) {
            ProductImage productImage = new ProductImage();
            productImage.setImage(file);
            productImage.setProduct(product);
           return productImageRepository.save(productImage);
        }
        return null;
    }

    @Override
    public void deleteImage(Integer id) {
        Optional<ProductImage> image = productImageRepository.findById(id);
        image.ifPresent(productImage -> productImageRepository.delete(productImage));
    }
}
