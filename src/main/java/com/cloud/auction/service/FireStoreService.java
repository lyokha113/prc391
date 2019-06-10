package com.cloud.auction.service;

import com.cloud.auction.entity.Account;
import com.cloud.auction.entity.Bidding;
import com.google.cloud.firestore.Firestore;

public interface FireStoreService {
    boolean insertBidding(Account account, String biddingId, Long price);
}
