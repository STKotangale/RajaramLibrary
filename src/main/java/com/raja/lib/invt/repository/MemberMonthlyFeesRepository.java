package com.raja.lib.invt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.raja.lib.invt.model.MemberMonthlyFees;


public interface MemberMonthlyFeesRepository extends JpaRepository<MemberMonthlyFees, Integer> {
	 @Query("SELECT COUNT(m) > 0 FROM MemberMonthlyFees m WHERE m.member.memberId = :memberId AND :dateStr BETWEEN m.fromDate AND m.toDate")
	    boolean existsByMemberAndDate(@Param("memberId") int memberId, @Param("dateStr") String dateStr);
	 
	 @Query(value = "SELECT COALESCE(MAX(memMonInvoiceNo), 0) + 1 AS next_invoice_number FROM acc_member_monthly_fees", nativeQuery = true)
	    int getNextInvoiceNumber();
	  
	}
