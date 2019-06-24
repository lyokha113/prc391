package com.cloud.auction.controller;

import com.cloud.auction.constant.RoleEnum;
import com.cloud.auction.entity.Account;
import com.cloud.auction.model.UserPrincipal;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.payload.CreateAccountRequest;
import com.cloud.auction.repository.AccountRepository;
import com.cloud.auction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
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

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getAccountDetail(Authentication auth) {
        if (auth != null) {
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            return ResponseEntity.ok(new ApiResponse<>(true, userPrincipal));
        }
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, "User role exception"));
    }

    @GetMapping("/admin/user")
    public ResponseEntity<ApiResponse> getAllUser(Authentication auth) {
        if (auth != null) {
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            if (userPrincipal.getAuthorities().get(0).getAuthority()
                    .equals(RoleEnum.ADMINISTRATOR.getName())) {
                List<Account> accounts = accountService.getAllAccount();
                return ResponseEntity.ok(new ApiResponse<>(true, accounts));
            }
        }
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, "User role exception"));
    }

}
