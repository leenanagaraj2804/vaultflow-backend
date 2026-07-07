package com.example.demo.controller;

import com.example.demo.dto.LedgerDTO;
import com.example.demo.service.LedgerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ledgers")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    @GetMapping
    public ResponseEntity<Page<LedgerDTO>> getAllLedgers(Pageable pageable) {
        return ResponseEntity.ok(ledgerService.getAllLedgers(pageable));
    }

    @PostMapping
    public ResponseEntity<LedgerDTO> createLedger(@Valid @RequestBody LedgerDTO ledgerDTO) {
        return new ResponseEntity<>(ledgerService.createLedger(ledgerDTO), HttpStatus.CREATED);
    }
}
