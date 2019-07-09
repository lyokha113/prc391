package com.cloud.auction.controller;

import com.cloud.auction.component.JwtTokenProvider;
import com.cloud.auction.model.Account;
import com.cloud.auction.model.UserPrincipal;
import com.cloud.auction.payload.ApiResponse;
import com.cloud.auction.payload.RegisterRequest;
import com.cloud.auction.payload.JwtAuthenticationResponse;
import com.cloud.auction.payload.LoginRequest;
import com.cloud.auction.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Account account = accountService.getAccountByEmail(loginRequest.getEmail());
            String jwt = tokenProvider.generateToken(account);
            return ResponseEntity.ok(new ApiResponse<>(true, new JwtAuthenticationResponse(jwt)));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "incorrect login"));
        } catch (LockedException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "account was locked"));
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "json parsing error"));
        }

    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerAccount(@Valid @RequestBody RegisterRequest request) {
        try {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
            accountService.registerAccount(request);
            Account account = accountService.getAccountByEmail(request.getEmail());
            return account != null ? ResponseEntity.ok(new ApiResponse<>(true, "account created")) :
                    ResponseEntity.ok(new ApiResponse<>(false, "account create failed"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

}
