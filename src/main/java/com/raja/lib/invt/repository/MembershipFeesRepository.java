package com.raja.lib.invt.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.MembershipFees;

@Repository
public interface MembershipFeesRepository extends JpaRepository<MembershipFees, Long> {
    @Query("SELECT COUNT(m) > 0 FROM MembershipFees m WHERE m.member.memberId = :memberId AND :date BETWEEN m.memInvoiceDate AND m.memInvoiceDate")
    boolean existsByMemberAndDate(@Param("memberId") int memberId, @Param("date") Date date);
 
    @Query(value = "SELECT MAX(amf.mem_invoice_no) + 1 AS max_invoice_no\r\n"
    		+ "FROM acc_membership_fees amf;\r\n"
    		+ "", nativeQuery = true)
    int getNextMembershipNo();
}
