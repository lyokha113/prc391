package com.cloud.auction.service;

import com.cloud.auction.model.Account;
import com.cloud.auction.model.Bidding;
import com.cloud.auction.model.Product;
import com.cloud.auction.payload.BiddingRequest;

import java.util.List;
import java.util.UUID;

public interface BiddingService {

    Bidding getBidding(String id);
    List<Bidding> getBids();
    List<Bidding> getBids(Product products);
    List<Bidding> getCurrentBids(List<Product> products);
    List<Bidding> getCurrentBidsOfUser(UUID uuid);
    List<Bidding> getFinishedBidsOfUser(UUID uuid);
    void createBidding(BiddingRequest request);
    void updateBidding(String id, BiddingRequest request);
    void updateWinner(Account account, Long money, Bidding bidding);

}
