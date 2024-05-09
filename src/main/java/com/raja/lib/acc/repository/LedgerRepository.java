package com.raja.lib.acc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.raja.lib.acc.model.Ledger;

public interface LedgerRepository extends JpaRepository<Ledger, Integer> {
}
