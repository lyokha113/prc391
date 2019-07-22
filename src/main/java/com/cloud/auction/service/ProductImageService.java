package com.cloud.auction.service;

import com.cloud.auction.model.ProductImage;

import java.util.List;

public interface ProductImageService {

    List<ProductImage> createImages(List<ProductImage> images);

}
