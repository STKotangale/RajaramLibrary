package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.raja.lib.invt.model.MembershipFees;

public interface MembershipFeesRepository extends JpaRepository<MembershipFees, Long> {
	
	@Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM MembershipFees m WHERE m.member.memberId = :memberId AND :date BETWEEN m.memInvoiceDate AND m.memInvoiceDate")
    boolean existsByMemberAndDate(@Param("memberId") int memberId, @Param("date") String date);
	
}
