package com.cloud.auction.service;

import com.cloud.auction.model.ProductImage;

import java.util.List;

public interface ProductImageService {

    ProductImage createImage(Integer id, String file);
    void deleteImage(Integer id);

}
