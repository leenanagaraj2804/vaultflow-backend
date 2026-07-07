package com.example.demo.service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuditService auditService;

    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AccountDTO createAccount(AccountDTO dto) {
        Account account = new Account();
        account.setAccountName(dto.getAccountName());
        account.setAccountType(dto.getAccountType());
        account.setBalance(dto.getBalance() != null ? dto.getBalance() : 0.0);
        account.setInterestRate(dto.getInterestRate());

        Account saved = accountRepository.save(account);
        auditService.logAction("Account created: " + saved.getAccountName());
        return convertToDTO(saved);
    }

    public AccountDTO updateAccount(Long id, AccountDTO dto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Account not found with id: " + id));

        if (dto.getAccountName() != null) {
            account.setAccountName(dto.getAccountName());
        }
        if (dto.getAccountType() != null) {
            account.setAccountType(dto.getAccountType());
        }
        if (dto.getBalance() != null) {
            account.setBalance(dto.getBalance());
        }
        if (dto.getInterestRate() != null) {
            account.setInterestRate(dto.getInterestRate());
        }

        Account updated = accountRepository.save(account);
        auditService.logAction("Account updated: " + updated.getId());
        return convertToDTO(updated);
    }

    private AccountDTO convertToDTO(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getAccountName(),
                account.getAccountType(),
                account.getBalance(),
                account.getInterestRate()
        );
    }
}