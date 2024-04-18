package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.raja.lib.invt.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
