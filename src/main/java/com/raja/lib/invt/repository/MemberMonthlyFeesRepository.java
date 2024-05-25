package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.raja.lib.invt.model.MemberMonthlyFees;

public interface MemberMonthlyFeesRepository extends JpaRepository<MemberMonthlyFees, Integer> {
	
	@Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM MemberMonthlyFees m WHERE m.member.memberId = :memberId AND :date BETWEEN m.fromDate AND m.toDate")
    boolean existsByMemberAndDate(@Param("memberId") int memberId, @Param("date") String date);
}
