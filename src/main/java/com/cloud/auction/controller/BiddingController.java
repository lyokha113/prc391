package com.cloud.auction.controller;

import com.cloud.auction.entity.Account;
import com.cloud.auction.entity.Bidding;
import com.cloud.auction.entity.Product;
import com.cloud.auction.model.UserPrincipal;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.payload.BidRequest;
import com.cloud.auction.payload.BiddingResponse;
import com.cloud.auction.service.AccountService;
import com.cloud.auction.service.BiddingService;
import com.cloud.auction.service.FireStoreService;
import com.cloud.auction.service.ProductService;
import com.cloud.auction.service.impl.FireStoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController

public class BiddingController {

    @Autowired
    private BiddingService biddingService;

    @Autowired
    private ProductService productService;

    private FireStoreService fireStoreService = new FireStoreServiceImpl();

    @GetMapping("/bid")
    private ResponseEntity<ApiResponse> getAllBidding() {
        List<Product> products = productService.getAllProduct();
        List<Bidding> bids = biddingService.getCurrentBids(products);
        return ResponseEntity.ok(new ApiResponse<>(true, bids.isEmpty() ? "empty" : bids));
    }

    @GetMapping("/bid/{id}")
    private ResponseEntity<ApiResponse> getBiding(@PathVariable("id") String id) {
        Optional<Bidding> result = biddingService.getBiddingById(id);
        if (result.isPresent()) {
            Bidding bid = result.get();
            List<Long> bidPrices = new ArrayList<>();
            for (int i = 1; i < 10; i++) {
                bidPrices.add(bid.getCurrentPrice() + (50000 * i));
            }

            return ResponseEntity.ok(new ApiResponse<>(true, new BiddingResponse(bid, bidPrices)));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "empty"));
    }

    @PostMapping("/bid")
    private ResponseEntity<ApiResponse> bid(@RequestBody BidRequest bidRequest, Authentication auth) {
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        fireStoreService.insertBidding(principal.getId().toString(), principal.getFullName(), bidRequest.getBidId(), bidRequest.getPrice());
        return ResponseEntity.ok(new ApiResponse<>(true, "done"));
    }
}
