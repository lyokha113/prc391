package com.cloud.auction.service.impl;

import com.cloud.auction.constant.RoleEnum;
import com.cloud.auction.exception.AppException;
import com.cloud.auction.model.Account;
import com.cloud.auction.model.Role;
import com.cloud.auction.payload.AccountRequest;
import com.cloud.auction.payload.RegisterRequest;
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
    public Account getAccountByEmail(String email) {
        return accountRepository.getByEmail(email).orElse(null);
    }

    @Override
    public Account getAccount(UUID uuid) {
        return accountRepository.findById(uuid).orElse(null);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void createAccount(RegisterRequest request) {
        Role role = new Role();
        role.setId(request.getRoleId());
        Account account = setAccountFromRequest(request, role);
        accountRepository.save(account);
    }

    private Account setAccountFromRequest(RegisterRequest request, Role role) {
        Account account = new Account();
        account.setPassword(request.getPassword());
        account.setFullName(request.getFullName());
        account.setEmail(request.getEmail());
        account.setPhone(request.getPhone());
        account.setRole(role);
        account.setActive(true);
        return account;
    }

    @Override
    public void updateAccount(UUID uuid, AccountRequest request) {
        Optional<Account> result = accountRepository.findById(uuid);
        if (result.isPresent()) {
            Account account = result.get();
            account.setActive(request.getActive());
            account.setAddress(request.getAddress());
            accountRepository.save(account);
        } else {
            throw new AppException("account not found");
        }
    }

    @Override
    public void registerAccount(RegisterRequest request) {
        Role role = new Role();
        role.setId(RoleEnum.CUSTOMER.getId());
        Account account = setAccountFromRequest(request, role);
        accountRepository.save(account);
    }
}
