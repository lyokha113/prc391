package com.cloud.auction.service;

import com.cloud.auction.entity.Bidding;
import com.cloud.auction.entity.Product;

import java.util.List;
import java.util.Optional;

public interface BiddingService {

    Optional<Bidding> getBiddingById(String id);

    List<Bidding> getCurrentBids(List<Product> products);

}
