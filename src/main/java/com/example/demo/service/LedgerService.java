package com.example.demo.service;

import com.example.demo.dto.LedgerDTO;
import com.example.demo.model.Ledger;
import com.example.demo.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LedgerService {

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private AuditService auditService;

    public Page<LedgerDTO> getAllLedgers(Pageable pageable) {
        return ledgerRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public LedgerDTO createLedger(LedgerDTO dto) {
        Ledger ledger = new Ledger();
        ledger.setDescription(dto.getDescription());
        ledger.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : new Date());

        Ledger saved = ledgerRepository.save(ledger);
        auditService.logAction("Ledger created: " + saved.getId());
        return convertToDTO(saved);
    }

    private LedgerDTO convertToDTO(Ledger ledger) {
        return new LedgerDTO(ledger.getId(), ledger.getDescription(), ledger.getCreatedAt());
    }
}
