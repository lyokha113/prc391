package com.cloud.auction.controller;

import com.cloud.auction.exception.AppException;
import com.cloud.auction.model.Bidding;
import com.cloud.auction.model.Product;
import com.cloud.auction.model.UserPrincipal;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.payload.BiddingRequest;
import com.cloud.auction.payload.ProductRequest;
import com.cloud.auction.service.AccountService;
import com.cloud.auction.service.OfferService;
import com.cloud.auction.service.BiddingService;
import com.cloud.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController

public class BiddingController {

    @Autowired
    private BiddingService biddingService;

    @Autowired
    private OfferService biddingHistoryService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @GetMapping("/admin/bidding")
    private ResponseEntity<ApiResponse> getBids() {
        List<Bidding> bids = biddingService.getBids();
        return ResponseEntity.ok(new ApiResponse<>(true, bids));
    }

    @GetMapping("/bidding")
    private ResponseEntity<ApiResponse> getCurrentBids() {
        List<Product> products = productService.getActiveProducts();
        List<Bidding> bids = biddingService.getCurrentBids(products);
        return ResponseEntity.ok(new ApiResponse<>(true, bids));
    }

    @GetMapping("/bidding/cate/{id}")
    private ResponseEntity<ApiResponse> getAllBiddingByCategory(@PathVariable("id") Integer categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        List<Bidding> bids = biddingService.getCurrentBids(products);
        return ResponseEntity.ok(new ApiResponse<>(true, bids));
    }

    @GetMapping("/bidding/user/current")
    private ResponseEntity<ApiResponse> getCurrentBidsOfUser(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<Bidding> bids = biddingService.getCurrentBidsOfUser(userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, bids));
    }

    @GetMapping("/bidding/user/finished")
    private ResponseEntity<ApiResponse> getFinishedBidsOfUser(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<Bidding> bids = biddingService.getFinishedBidsOfUser(userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, bids));
    }

    @GetMapping("/bidding/{id}")
    private ResponseEntity<ApiResponse> getBiding(@PathVariable("id") String id) {
        Bidding bidding = biddingService.getBidding(id);
        if (bidding != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, bidding));
        }
        return ResponseEntity.ok(new ApiResponse<>(false, "bidding not found"));
    }

    @PostMapping("/bidding")
    private ResponseEntity<ApiResponse> createBidding(@Valid @RequestBody BiddingRequest request) {
        Bidding bidding = biddingService.createBidding(request);
        return ResponseEntity.ok(new ApiResponse<>(true, bidding));
    }


    @PutMapping("/bidding/{id}")
    private ResponseEntity<ApiResponse> updateBidding(@PathVariable("id") String id,
                                                      @Valid @RequestBody BiddingRequest request) {
        try {
            biddingService.updateBidding(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "updated successfully"));
        } catch (AppException ex) {
            return ResponseEntity.ok(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PutMapping("/bidding/close/{id}")
    private ResponseEntity<ApiResponse> closeBidding(@PathVariable("id") String id) {
        try {
            List<String> ids = new ArrayList<>();
            ids.add(id);
            biddingService.updateExpiredBidding(ids);
            return ResponseEntity.ok(new ApiResponse<>(true, "updated successfully"));
        } catch (AppException ex) {
            return ResponseEntity.ok(new ApiResponse<>(false, ex.getMessage()));
        }
    }


}
