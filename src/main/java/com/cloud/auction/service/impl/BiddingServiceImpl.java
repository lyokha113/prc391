package com.cloud.auction.service.impl;

import com.cloud.auction.entity.Bidding;
import com.cloud.auction.entity.Product;
import com.cloud.auction.repository.BiddingRepository;
import com.cloud.auction.service.BiddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BiddingServiceImpl implements BiddingService {

    @Autowired
    private BiddingRepository biddingRepository;

    @Override
    public Optional<Bidding> getBiddingById(String id) {
        return biddingRepository.findById(id);
    }

    @Override
    public List<Bidding> getCurrentBids(List<Product> products) {
        List<Bidding> result = new ArrayList<>();

        products.forEach(product -> result.addAll(
                product.getBids().stream()
                        .filter(bid -> bid.getEndTime().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList())
        ));

        return result;
    }
}
