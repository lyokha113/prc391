package com.cloud.auction.controller;

import com.cloud.auction.entity.Bidding;
import com.cloud.auction.entity.Product;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.service.BiddingService;
import com.cloud.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController

public class BiddingController {

    @Autowired
    private BiddingService biddingService;

    @Autowired
    private ProductService productService;

    @GetMapping("/bids")
    private ResponseEntity<?> getAllBidding() {
        List<Product> products = productService.getAllProduct();
        List<Bidding> bids = biddingService.getCurrentBids(products);
        return ResponseEntity.ok(new ApiResponse<>(true, bids.isEmpty() ? "empty" : bids));
    }

    @GetMapping("/bids/{id}")
    private ResponseEntity<?> getBiding(@PathVariable("id") String id) {
        Optional<Bidding> bid = biddingService.getBiddingById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, bid.isPresent()? bid.get() : "empty"));
    }
}
