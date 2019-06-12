package com.cloud.auction.service;

public interface FireStoreService {
    void insertBidding(String accountId, String accountName, String biddingId, Long price);
}
