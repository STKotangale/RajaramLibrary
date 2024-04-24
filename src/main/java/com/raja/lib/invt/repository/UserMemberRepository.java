package com.raja.lib.invt.repository;

import com.raja.lib.invt.model.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMemberRepository extends JpaRepository<UserMember, Integer> {
}
