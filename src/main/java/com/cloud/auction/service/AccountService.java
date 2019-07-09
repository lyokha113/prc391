package com.cloud.auction.service;

import com.cloud.auction.model.Account;
import com.cloud.auction.model.Role;
import com.cloud.auction.payload.AccountRequest;
import com.cloud.auction.payload.RegisterRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {

    Account getAccountByEmail(String email);
    Account getAccount(UUID uuid);
    List<Account> getAccounts();
    void createAccount(RegisterRequest request);
    void updateAccount(UUID uuid, AccountRequest request);
    void registerAccount(RegisterRequest request);
}
