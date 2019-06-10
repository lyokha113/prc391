package com.cloud.auction.repository;

import com.cloud.auction.entity.Product;
import com.cloud.auction.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

}
