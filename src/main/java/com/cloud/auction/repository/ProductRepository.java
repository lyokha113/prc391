package com.cloud.auction.repository;

import com.cloud.auction.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> getAllByActiveTrueAndCategory_Id(Integer id);
    List<Product> getAllByActiveTrue();
}
