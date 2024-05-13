package com.raja.lib.acc.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.raja.lib.acc.model.Ledger;
import com.raja.lib.acc.repository.LedgerRepository;
import com.raja.lib.acc.request.LedgerRequestDTO;
import com.raja.lib.acc.response.ApiResponseDTO;


@Service

public class LedgerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LedgerService.class);

    @Autowired
    private LedgerRepository ledgerRepository;

    public ApiResponseDTO<Ledger> createLedger(LedgerRequestDTO request) {
        LOGGER.info("Creating ledger");
        Ledger ledger = new Ledger();
        ledger.setLedgerName(request.getLedgerName());
        ledger.setIsBlock(request.getIsBlock());
        Ledger savedLedger = ledgerRepository.save(ledger);
        LOGGER.debug("Ledger created with id {}", savedLedger.getLedgerID());
        return new ApiResponseDTO<>(true, "Ledger created successfully", savedLedger, 201);
    }
    
    @Cacheable(value = "Ledgers", key = "#ledgerId")
    public ApiResponseDTO<Ledger> getLedgerById(int ledgerId) {
        LOGGER.info("Fetching ledger with id {}", ledgerId);
        Optional<Ledger> optionalLedger = ledgerRepository.findById(ledgerId);
        if (optionalLedger.isPresent()) {
            Ledger ledger = optionalLedger.get();
            LOGGER.debug("Ledger found with id {}", ledgerId);
            return new ApiResponseDTO<>(true, "Ledger found", ledger, 200);
        } else {
            LOGGER.warn("Ledger not found with id {}", ledgerId);
            return new ApiResponseDTO<>(false, "Ledger not found with id: " + ledgerId, null, 404);
        }
    }

    @CacheEvict(value = "allLedger", allEntries = true)
    public ApiResponseDTO<List<Ledger>> getAllLedgers() {
        LOGGER.info("Fetching all ledgers");
        List<Ledger> ledgers = ledgerRepository.findAll();
        LOGGER.debug("Found {} ledgers", ledgers.size());
        return new ApiResponseDTO<>(true, "All ledgers retrieved successfully", ledgers, 200);
    }

    public ApiResponseDTO<Ledger> updateLedger(int ledgerId, LedgerRequestDTO request) {
        LOGGER.info("Updating ledger with id {}", ledgerId);
        Optional<Ledger> optionalLedger = ledgerRepository.findById(ledgerId);
        if (optionalLedger.isPresent()) {
            Ledger ledger = optionalLedger.get();
            ledger.setLedgerName(request.getLedgerName());
            ledger.setIsBlock(request.getIsBlock());
            Ledger updatedLedger = ledgerRepository.save(ledger);
            LOGGER.debug("Ledger updated with id {}", ledgerId);
            return new ApiResponseDTO<>(true, "Ledger updated successfully", updatedLedger, 200);
        } else {
            LOGGER.warn("Ledger not found with id {}", ledgerId);
            return new ApiResponseDTO<>(false, "Ledger not found with id: " + ledgerId, null, 404);
        }
    }

    public ApiResponseDTO<Void> deleteLedger(int ledgerId) {
        LOGGER.info("Deleting ledger with id {}", ledgerId);
        if (ledgerRepository.existsById(ledgerId)) {
            try {
                ledgerRepository.deleteById(ledgerId);
                LOGGER.debug("Ledger deleted with id {}", ledgerId);
                return new ApiResponseDTO<>(true, "Ledger deleted successfully", null, 200);
            } catch (Exception e) {
                LOGGER.error("Failed to delete ledger with id {}", ledgerId, e);
                return new ApiResponseDTO<>(false, "Failed to delete ledger", null, 500);
            }
        } else {
            LOGGER.warn("Ledger not found with id {}", ledgerId);
            return new ApiResponseDTO<>(false, "Ledger not found with id: " + ledgerId, null, 404);
        }
    }

}
