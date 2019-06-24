package com.cloud.auction.service.impl;

import com.cloud.auction.entity.Account;
import com.cloud.auction.repository.AccountRepository;
import com.cloud.auction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Optional<Account> getAccountById(UUID uuid) {
        return accountRepository.findById(uuid);
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public void insertAccount(Account account) {
        accountRepository.save(account);
    }
}
