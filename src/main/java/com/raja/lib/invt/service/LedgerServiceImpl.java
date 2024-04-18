package com.raja.lib.invt.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.raja.lib.invt.model.Ledger;
import com.raja.lib.invt.repository.LedgerRepository;
import com.raja.lib.invt.request.LedgerRequest;

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

}
