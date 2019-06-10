package com.cloud.auction.service;


import com.google.cloud.firestore.Firestore;

public interface FireStoreService {
    void insertBidding(String accountId, String accountName, String biddingId, Long price);
}
