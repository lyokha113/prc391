package com.cloud.auction.service.impl;

import com.cloud.auction.entity.Account;
import com.cloud.auction.model.UserPrincipal;
import com.cloud.auction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountService.getAccountByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with  email : " + email));
        return UserPrincipal.create(account);
    }

    @Transactional
    public UserDetails loadUSerById(UUID uuid) throws UsernameNotFoundException {
        Account account = accountService.getAccountById(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with  id : " + uuid));
        return UserPrincipal.create(account);
    }
}
