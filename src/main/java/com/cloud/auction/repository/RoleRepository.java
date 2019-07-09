package com.cloud.auction.repository;

import com.cloud.auction.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<ProductImage, Integer> {

}
