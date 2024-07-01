package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.MembershipFees;

@Repository
public interface MembershipFeesRepository extends JpaRepository<MembershipFees, Integer> {

	@Query(value = "SELECT IF(COUNT(mf.membershipId) > 0 AND COUNT(mmf.memberMonthlyId) > 0, 1, 0) AS fees_paid "
			+ "FROM (SELECT 1) AS dummy " + "LEFT JOIN acc_membership_fees mf ON mf.memberIdF = :memberId "
			+ "LEFT JOIN acc_member_monthly_fees mmf ON mmf.memberIdF = :memberId AND :date BETWEEN mmf.fromDate AND mmf.toDate", nativeQuery = true)
	int hasPaidFees(@Param("memberId") int memberId, @Param("date") String date);

	@Query(value = "SELECT MAX(amf.mem_invoice_no) + 1 FROM acc_membership_fees amf", nativeQuery = true)
	Integer getNextMembershipNo();

}
