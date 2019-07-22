package com.cloud.auction.controller;

import com.cloud.auction.model.Account;
import com.cloud.auction.model.UserPrincipal;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getAccountDetail(Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Account account = accountService.getAccount(userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, account));
    }
}
