package com.cloud.auction.service;

import com.cloud.auction.model.Account;
import com.cloud.auction.payload.OfferRequest;

public interface OfferService {

    void bid(Account account, OfferRequest request);
}
