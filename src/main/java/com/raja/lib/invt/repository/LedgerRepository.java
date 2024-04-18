package com.raja.lib.invt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raja.lib.invt.model.Ledger;

public interface LedgerRepository extends JpaRepository<Ledger, Integer> {

	Optional<Ledger> findByLedgerCode(String ledgerCode);

}
