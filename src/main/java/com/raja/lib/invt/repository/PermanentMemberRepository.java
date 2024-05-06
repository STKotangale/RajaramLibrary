package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raja.lib.auth.model.PermanentMember;

@Repository
public interface PermanentMemberRepository extends JpaRepository<PermanentMember, Integer> {}
