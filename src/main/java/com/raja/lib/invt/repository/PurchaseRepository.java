//package com.raja.lib.invt.repository;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import com.raja.lib.invt.model.Purchase;
//
//@Repository
//public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
//	
////	@Query("SELECT p FROM Purchase p JOIN FETCH p.ledger WHERE p.purchaseId = :purchaseId")
////    Optional<Purchase> findByIdAndFetchLedgerEagerly(@Param("purchaseId") Long purchaseId);
//}
