package com.cloud.auction.service.impl;


import com.cloud.auction.model.ProductImage;
import com.cloud.auction.repository.ProductImageRepository;
import com.cloud.auction.service.FirebaseService;
import com.cloud.auction.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private FirebaseService firebaseService;

    public List<ProductImage> createImages(List<ProductImage> images) {
//        firebaseService.uploadImages("/p10" , request);
        return productImageRepository.saveAll(images);
    }
}
