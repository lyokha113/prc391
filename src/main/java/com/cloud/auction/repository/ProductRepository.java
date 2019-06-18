package com.cloud.auction.repository;

import com.cloud.auction.entity.Category;
import com.cloud.auction.entity.Product;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> getAllByCategory_Id(Integer id);
}
