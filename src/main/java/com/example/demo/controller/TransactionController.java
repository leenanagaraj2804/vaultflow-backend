package com.example.demo.controller;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/ledger/{ledgerId}")
    public ResponseEntity<List<TransactionDTO>> getByLedger(@PathVariable String ledgerId) {
        return ResponseEntity.ok(transactionService.getByLedger(ledgerId));
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.createTransaction(transactionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, transactionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> reverseTransaction(@PathVariable Long id) {
        transactionService.reverseTransaction(id);
        return ResponseEntity.ok("Transaction reversed successfully.");
    }
}
