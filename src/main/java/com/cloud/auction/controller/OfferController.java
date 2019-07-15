package com.cloud.auction.controller;


import com.cloud.auction.model.Account;
import com.cloud.auction.model.UserPrincipal;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.payload.OfferRequest;
import com.cloud.auction.service.AccountService;
import com.cloud.auction.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OfferController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private OfferService offerService;


    @PostMapping("/offer")
    private ResponseEntity<ApiResponse> bid(@Valid @RequestBody OfferRequest offerRequest, Authentication auth) {
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        Account account = accountService.getAccount(principal.getId());
        if (account != null) {
            offerService.bid(account, offerRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "bid successfully"));
        }
        return ResponseEntity.ok(new ApiResponse<>(false, "bid failed"));
    }
}
