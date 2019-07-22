package com.cloud.auction.service.impl;

import com.cloud.auction.exception.AppException;
import com.cloud.auction.model.Account;
import com.cloud.auction.model.Bidding;
import com.cloud.auction.model.Offer;
import com.cloud.auction.model.Product;
import com.cloud.auction.payload.BiddingRequest;
import com.cloud.auction.repository.BiddingRepository;
import com.cloud.auction.service.AccountService;
import com.cloud.auction.service.BiddingService;
import com.cloud.auction.service.ProductService;
import com.cloud.auction.utils.BiddingUtils;
import com.cloud.auction.utils.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BiddingServiceImpl implements BiddingService {

    @Autowired
    private BiddingRepository biddingRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private AccountService accountService;

    @Override
    public Bidding getBidding(String id) {
        return biddingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Bidding> getBids() {
        return biddingRepository.findAll();
    }

    @Override
    public List<Bidding> getBids(Product products) {
        return biddingRepository.getAllByProduct_Id(products.getId());
    }

    @Override
    public List<Bidding> getCurrentBids(List<Product> products) {
        List<Bidding> bids = new ArrayList<>();
        products.forEach(product -> bids.addAll(
                product.getBids().stream()
                        .filter(BiddingUtils::isCurrent)
                        .collect(Collectors.toList())
        ));
        return bids;
    }

    @Override
    public List<Bidding> getCurrentBidsOfUser(UUID uuid) {
        List<Bidding> bids = getBidsOfUser(uuid);
        return bids.stream().filter(BiddingUtils::isCurrent)
                .collect(Collectors.toList());
    }

    @Override
    public List<Bidding> getFinishedBidsOfUser(UUID uuid) {
        List<Bidding> bids = getBidsOfUser(uuid);
        return bids.stream().filter(BiddingUtils::isFinished)
                .collect(Collectors.toList());
    }

    @Override
    public Bidding createBidding(BiddingRequest request) {
        Product product = productService.getProduct(request.getProductId());
        if (product != null) {
            Bidding bidding = new Bidding();
            bidding.setId(BiddingUtils.generateId(product));
            bidding.setCurrentPrice(product.getPrice());
            bidding.setEndTime(LocalDateTime.now().plusHours(request.getDuration()));
            bidding.setExpired(false);
            bidding.setFinished(false);
            bidding.setProduct(product);
            return biddingRepository.save(bidding);
        } else {
            throw new AppException("product not found");
        }
    }

    @Override
    public void updateBidding(String id, BiddingRequest request) {
        Bidding bidding = getBidding(id);
        if (bidding != null) {
            bidding.setEndTime(bidding.getStartTime().plusHours(request.getDuration()));
            biddingRepository.save(bidding);
        } else {
            throw new AppException("bidding not found");
        }
    }

    @Override
    public void updateExpiredBidding(List<String> id) {
        List<Bidding> bids = new ArrayList<>();
        id.forEach(i -> {
            Bidding bidding = getBidding(i);
            if (bidding != null) {
                bidding.setExpired(true);
                bidding.setFinished(false);
                bids.add(bidding);
            }
        });
        biddingRepository.saveAll(bids);
    }

    @Override
    public void updateFinishedBidding(List<String> id) {
        List<Bidding> bids = new ArrayList<>();
        id.forEach(i -> {
            Bidding bidding = getBidding(i);
            if (bidding != null) {
                bidding.setFinished(true);
                bidding.setExpired(false);
                bids.add(bidding);
            }
        });
        biddingRepository.saveAll(bids);
    }


    @Override
    public void updateWinner(Account account, Long money, Bidding bidding) {
        bidding.setWinner(account);
        bidding.setCurrentPrice(money);
        biddingRepository.save(bidding);
    }

    private List<Bidding> getBidsOfUser(UUID uuid) {
        Account account = accountService.getAccount(uuid);
        List<Offer> histories = account.getOffers();
        return histories.stream()
                .filter(StreamUtils.distinctByKey(Offer::getBidding))
                .map(Offer::getBidding)
                .collect(Collectors.toList());
    }
}
