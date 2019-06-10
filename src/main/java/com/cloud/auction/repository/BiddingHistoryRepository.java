package com.cloud.auction.repository;

import com.cloud.auction.entity.Bidding;
import com.cloud.auction.entity.BiddingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiddingHistoryRepository extends JpaRepository<BiddingHistory, Integer> {

}
