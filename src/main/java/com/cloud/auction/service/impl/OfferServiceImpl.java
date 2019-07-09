package com.cloud.auction.service.impl;

import com.cloud.auction.exception.AppException;
import com.cloud.auction.model.Account;
import com.cloud.auction.model.Bidding;
import com.cloud.auction.payload.BidRequest;
import com.cloud.auction.service.OfferService;
import com.cloud.auction.service.BiddingService;
import com.cloud.auction.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BiddingHistoryServiceImpl implements OfferService {

    @Autowired
    private OfferService biddingHistoryService;

    @Autowired
    private BiddingService biddingService;

    @Autowired
    private FirebaseService firebaseService;

    @Override
    public void bid(Account account, BidRequest request) {
        Bidding bidding = biddingService.getBidding(request.getBidId());
        if (bidding != null) {
            Account winner = bidding.getWinner();
            if (winner.getId().equals(account.getId())) {
                throw new AppException("top bid user");
            }

            Long maxMoney = bidding.getCurrentPrice();
            if (request.getMoney() <= maxMoney) {
                throw new AppException("money not enough");
            }

            firebaseService.insertBidding(account.getId(), account.getFullName(),
                    request.getBidId(), request.getMoney());

            bidding.setWinner(account);
            bidding.setCurrentPrice(request.getMoney());
            biddingService.updateBidding(bidding);

        } else {
            throw new AppException("bidding not found");
        }

    }
}
