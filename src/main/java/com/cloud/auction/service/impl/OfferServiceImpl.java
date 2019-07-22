package com.cloud.auction.service.impl;

import com.cloud.auction.exception.AppException;
import com.cloud.auction.model.Account;
import com.cloud.auction.model.Bidding;
import com.cloud.auction.model.Offer;
import com.cloud.auction.payload.OfferRequest;
import com.cloud.auction.repository.OfferRepository;
import com.cloud.auction.service.BiddingService;
import com.cloud.auction.service.FirebaseService;
import com.cloud.auction.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private BiddingService biddingService;

    @Autowired
    private FirebaseService firebaseService;

    @Override
    public void bid(Account account, OfferRequest request) {
        Bidding bidding = biddingService.getBidding(request.getBiddingId());
        if (bidding != null) {
            Account winner = bidding.getWinner();
            if (winner != null && winner.getId().equals(account.getId())) {
                throw new AppException("top bid user");
            }

            Long maxMoney = bidding.getCurrentPrice();
            if (request.getMoney() <= maxMoney) {
                throw new AppException("money not enough");
            }

            firebaseService.insertBidding(account.getId(), account.getFullName(),
                    request.getBiddingId(), request.getMoney());

            firebaseService.createPriceList(request.getBiddingId(),request.getMoney() );

            biddingService.updateWinner(account, request.getMoney(), bidding);
            createOffer(account, bidding, request.getMoney());
        } else {
            throw new AppException("bidding not found");
        }

    }

    @Override
    public void createOffer(Account account, Bidding bidding, Long price) {
        Offer offer = new Offer();
        offer.setAccount(account);
        offer.setBidding(bidding);
        offer.setPrice(price);
        offer.setDate(LocalDateTime.now());
        offer.setStatus(0);
        offerRepository.save(offer);
    }
}
