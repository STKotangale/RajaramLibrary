package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.raja.lib.invt.model.MembershipFeesDetail;

public interface MembershipFeesDetailRepository extends JpaRepository<MembershipFeesDetail, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM MembershipFeesDetail m WHERE m.membershipIdF.id = :membershipId")
    void deleteByMembershipIdF(@Param("membershipId") Integer membershipId);
}
