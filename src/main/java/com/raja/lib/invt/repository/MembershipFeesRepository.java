package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.MembershipFees;

@Repository
public interface MembershipFeesRepository extends JpaRepository<MembershipFees, Integer> {

	@Query(value = "SELECT IF(EXISTS (SELECT 1 FROM acc_membership_fees mf WHERE mf.memberIdF = :memberId) "
			+ "AND EXISTS (SELECT 1 FROM acc_member_monthly_fees mmf WHERE mmf.memberIdF = :memberId "
			+ "AND STR_TO_DATE(:date, '%d-%m-%Y') BETWEEN STR_TO_DATE(mmf.fromDate, '%d-%m-%Y') AND STR_TO_DATE(mmf.toDate, '%d-%m-%Y')), 1, 0) AS fees_paid", nativeQuery = true)
	int hasPaidFees(@Param("memberId") int memberId, @Param("date") String date);

	@Query(value = "SELECT MAX(amf.mem_invoice_no) + 1 FROM acc_membership_fees amf", nativeQuery = true)
	Integer getNextMembershipNo();

}
