package com.cloud.auction.controller;

import com.cloud.auction.entity.Account;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.payload.CreateAccountRequest;
import com.cloud.auction.repository.AccountRepository;
import com.cloud.auction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequest request) {

        try {
            Account account = new Account(request);
            account.setPassword(passwordEncoder.encode(request.getPassword()));

            accountService.insertAccount(account);
            Optional<Account> result = accountService.getAccountById(account.getId());
            return result.isPresent() ? ResponseEntity.ok(new ApiResponse<>(true, "account created")) :
                    ResponseEntity.ok(new ApiResponse<>(false, "account create failed"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage()));
        }

    }
}
