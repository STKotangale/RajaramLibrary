package com.raja.lib.invt.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.Ledger;
import com.raja.lib.invt.repository.LedgerRepository;
import com.raja.lib.invt.request.LedgerRequest;
import com.raja.lib.invt.resposne.ApiResponseDTO;

@Service
public class LedgerServiceImpl   {

	private final LedgerRepository ledgerRepository;
	private static final Logger logger = LoggerFactory.getLogger(LedgerServiceImpl.class);

	public LedgerServiceImpl(LedgerRepository ledgerRepository) {
		this.ledgerRepository = ledgerRepository;
	}

	
	public List<Ledger> getAllLedger() {
		try {
			return ledgerRepository.findAll();
		} catch (Exception e) {
			logger.error("Error retrieving all ledgers", e);
			throw e;
		}
	}

	
	public Optional<Ledger> getLedgerById(int id) {
		try {
			return ledgerRepository.findById(id);
		} catch (Exception e) {
			logger.error("Error retrieving ledger with ID: {}", id, e);
			throw e;
		}
	}

	public Ledger saveLedger(LedgerRequest ledgerRequest) {
		try {
			Ledger ledger = new Ledger();
			ledger.setLedgerCode(ledgerRequest.getLedgerCode());
			ledger.setLedgerName(ledgerRequest.getLedgerName());
			return ledgerRepository.save(ledger);
		} catch (Exception e) {

			return null;
		}
	}
	
	public ApiResponseDTO<Void> deleteLedgerById(int id) {
        ApiResponseDTO<Void> response = new ApiResponseDTO<>();
        try {
            ledgerRepository.deleteById(id);
            response.setSuccess(true);
            response.setMessage("Ledger deleted successfully");
            response.setStatusCode(HttpStatus.OK.value());
        } catch (NoSuchElementException e) {
            response.setSuccess(false);
            response.setMessage("No ledger found with ID: " + id);
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
        } catch (Exception e) {
            logger.error("Error deleting ledger with ID: {}", id, e);
            response.setSuccess(false);
            response.setMessage("Internal server error");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }
	
    public Ledger updateLedger(int id, LedgerRequest ledgerRequest) {
        try {
            Optional<Ledger> ledgerOptional = ledgerRepository.findById(id);
            if (!ledgerOptional.isPresent()) {
                throw new IllegalArgumentException("No ledger found with ID: " + id);
            }

            Ledger existingLedger = ledgerOptional.get();
            existingLedger.setLedgerCode(ledgerRequest.getLedgerCode());
            existingLedger.setLedgerName(ledgerRequest.getLedgerName());

            return ledgerRepository.save(existingLedger);
        } catch (Exception e) {
            logger.error("Error updating ledger with ID: {}", id, e);
            throw e;
        }
    }

}
