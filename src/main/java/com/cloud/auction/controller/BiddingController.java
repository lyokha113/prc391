package com.cloud.auction.controller;

import com.cloud.auction.entity.Account;
import com.cloud.auction.entity.Bidding;
import com.cloud.auction.entity.Product;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.payload.BidRequest;
import com.cloud.auction.payload.BiddingResponse;
import com.cloud.auction.service.AccountService;
import com.cloud.auction.service.BiddingService;
import com.cloud.auction.service.FireStoreService;
import com.cloud.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController

public class BiddingController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private BiddingService biddingService;

    @Autowired
    private ProductService productService;

    private FireStoreService fireStoreService;

    @GetMapping("/bids")
    private ResponseEntity<?> getAllBidding() {
        List<Product> products = productService.getAllProduct();
        List<Bidding> bids = biddingService.getCurrentBids(products);
        return ResponseEntity.ok(new ApiResponse<>(true, bids.isEmpty() ? "empty" : bids));
    }

    @GetMapping("/bid/{id}")
    private ResponseEntity<?> getBiding(@PathVariable("id") String id) {
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

    @PostMapping("/bid/")
    private ResponseEntity<?> bid(@RequestBody BidRequest bidRequest) {
        Optional<Account> account = accountService.getAccountById(UUID.fromString(bidRequest.getAccountId()));
        if (!account.isPresent()) return ResponseEntity.ok(new ApiResponse<>(false, "account not found"));

        boolean result = fireStoreService.insertBidding(account.get(), bidRequest.getBidId(), bidRequest.getMoney());
        return ResponseEntity.ok(new ApiResponse<>(result, result ? "failed" : "done"));
    }
}