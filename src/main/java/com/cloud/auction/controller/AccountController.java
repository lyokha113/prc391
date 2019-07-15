package com.cloud.auction.controller;

import com.cloud.auction.exception.AppException;
import com.cloud.auction.model.Account;
import com.cloud.auction.model.UserPrincipal;
import com.cloud.auction.payload.AccountRequest;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.payload.RegisterRequest;
import com.cloud.auction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getAccountDetail(Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Account account = accountService.getAccount(userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse<>(true, account));
    }

    @GetMapping("/account")
    public ResponseEntity<ApiResponse> getAccounts() {
        List<Account> accounts = accountService.getAccounts();
        return ResponseEntity.ok(new ApiResponse<>(true, accounts));
    }
    @PostMapping("/account")
    public ResponseEntity<ApiResponse> createAccount(@Valid @RequestBody RegisterRequest request) {
        try {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
            accountService.createAccount(request);
            Account account = accountService.getAccountByEmail(request.getEmail());
            return account != null ? ResponseEntity.ok(new ApiResponse<>(true, "account created")) :
                    ResponseEntity.ok(new ApiResponse<>(false, "account create failed"));
        } catch (Exception ex) {
            return ResponseEntity.ok(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PutMapping("/account/{id}")
    public ResponseEntity<ApiResponse> updateAccount(@PathVariable("id") UUID uuid,
                                                     @Valid @RequestBody AccountRequest request) {
        try {
            accountService.updateAccount(uuid, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "updated successfully"));
        } catch (AppException ex) {
            return ResponseEntity.ok(new ApiResponse<>(false, ex.getMessage()));
        }
    }

}
