package com.example.demo.service;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.exception.InsufficientFundsException;
import com.example.demo.model.Account;
import com.example.demo.model.Transaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuditService auditService;

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Transaction not found with id: " + id));
        return convertToDTO(transaction);
    }

    public List<TransactionDTO> getByLedger(String ledgerId) {
        return transactionRepository.findByReferenceIdContainingIgnoreCase(ledgerId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TransactionDTO createTransaction(TransactionDTO dto) {
        Account source = accountRepository.findById(dto.getSourceAccountId())
                .orElseThrow(() -> new NoSuchElementException("Source account not found"));
        Account target = accountRepository.findById(dto.getTargetAccountId())
                .orElseThrow(() -> new NoSuchElementException("Target account not found"));

        if (source.getBalance() < dto.getAmount()) {
            throw new InsufficientFundsException("Insufficient funds in source account: " + source.getId());
        }

        source.setBalance(source.getBalance() - dto.getAmount());
        target.setBalance(target.getBalance() + dto.getAmount());
        accountRepository.save(source);
        accountRepository.save(target);

        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setReferenceId(dto.getReferenceId());
        transaction.setSourceAccount(source);
        transaction.setTargetAccount(target);
        transaction.setStatus("PENDING");

        Transaction saved = transactionRepository.save(transaction);
        auditService.logAction("Transaction created: " + saved.getReferenceId());
        return convertToDTO(saved);
    }

    @Transactional
    public TransactionDTO updateTransaction(Long id, TransactionDTO dto) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Transaction not found with id: " + id));

        if (dto.getStatus() != null) {
            transaction.setStatus(dto.getStatus());
        }
        if (dto.getAmount() != null) {
            transaction.setAmount(dto.getAmount());
        }

        Transaction updated = transactionRepository.save(transaction);
        auditService.logAction("Transaction updated: " + updated.getId() + " -> " + updated.getStatus());
        return convertToDTO(updated);
    }

    @Transactional
    public TransactionDTO reverseTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Transaction not found with id: " + id));

        if ("REVERSED".equals(transaction.getStatus())) {
            throw new IllegalStateException("Transaction already reversed: " + id);
        }

        Account source = transaction.getSourceAccount();
        Account target = transaction.getTargetAccount();

        if (target.getBalance() < transaction.getAmount()) {
            throw new InsufficientFundsException("Insufficient funds in target account to reverse transaction: " + id);
        }

        target.setBalance(target.getBalance() - transaction.getAmount());
        source.setBalance(source.getBalance() + transaction.getAmount());
        accountRepository.save(source);
        accountRepository.save(target);

        transaction.setStatus("REVERSED");
        Transaction reversed = transactionRepository.save(transaction);
        auditService.logAction("Transaction reversed: " + reversed.getId());
        return convertToDTO(reversed);
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getReferenceId(),
                transaction.getSourceAccount() != null ? transaction.getSourceAccount().getId() : null,
                transaction.getTargetAccount() != null ? transaction.getTargetAccount().getId() : null,
                transaction.getStatus()
        );
    }
}
