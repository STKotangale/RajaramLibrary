package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.raja.lib.invt.model.MemberMonthlyFees;

public interface MemberMonthlyFeesRepository extends JpaRepository<MemberMonthlyFees, Long> {
}
