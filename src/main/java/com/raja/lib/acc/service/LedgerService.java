package com.raja.lib.acc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raja.lib.acc.model.Ledger;
import com.raja.lib.acc.repository.LedgerRepository;
import com.raja.lib.acc.request.LedgerRequestDTO;

@Service
public class LedgerService {

    @Autowired
    private LedgerRepository ledgerRepository;

    public Ledger createLedger(LedgerRequestDTO request) {
        Ledger ledger = new Ledger(request.getLedgerName());
        ledger.setIsBlock(request.getIsBlock());
        return ledgerRepository.save(ledger);
    }

    
    public Ledger getLedgerById(int ledgerId) {
        Optional<Ledger> optionalLedger = ledgerRepository.findById(ledgerId);
        return optionalLedger.orElse(null);
    }

    
    public List<Ledger> getAllLedgers() {
        return ledgerRepository.findAll();
    }

    
    public Ledger updateLedger(int ledgerId, LedgerRequestDTO request) {
        Optional<Ledger> optionalLedger = ledgerRepository.findById(ledgerId);
        if (optionalLedger.isPresent()) {
            Ledger ledger = optionalLedger.get();
            ledger.setLedgerName(request.getLedgerName());
            ledger.setIsBlock(request.getIsBlock());
            return ledgerRepository.save(ledger);
        }
        return null;
    }

    
    public void deleteLedger(int ledgerId) {
        ledgerRepository.deleteById(ledgerId);
    }
}