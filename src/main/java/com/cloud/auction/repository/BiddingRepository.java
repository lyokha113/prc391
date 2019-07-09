package com.cloud.auction.repository;

import com.cloud.auction.model.Bidding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiddingRepository extends JpaRepository<Bidding, String> {
    List<Bidding> getAllByProduct_Id(Integer id);
}
