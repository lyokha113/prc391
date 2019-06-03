package com.cloud.auction.service;

import com.cloud.auction.entity.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountService {

    Optional<Account> getAccountByEmail(String email);
    Optional<Account> getAccountById(UUID uuid);
    void  insertAccount(Account account);
}
