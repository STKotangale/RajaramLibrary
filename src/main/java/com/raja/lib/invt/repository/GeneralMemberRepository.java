package com.raja.lib.invt.repository;

import com.raja.lib.invt.model.GeneralMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralMemberRepository extends JpaRepository<GeneralMember, Integer> {
}
