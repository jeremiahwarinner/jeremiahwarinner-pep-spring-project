package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account register(Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty() ||
            account.getPassword().length() < 4) {
            return null;
        }
        
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        
        return accountRepository.save(account);
    }

    public Account login(Account account) {
        return accountRepository.findByUsernameAndPassword(
            account.getUsername(), 
            account.getPassword()
        );
    }
}