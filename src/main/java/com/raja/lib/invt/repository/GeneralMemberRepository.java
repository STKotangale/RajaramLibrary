package com.raja.lib.invt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.raja.lib.auth.model.GeneralMember;
import com.raja.lib.invt.objects.GenralMember;

@Repository
public interface GeneralMemberRepository extends JpaRepository<GeneralMember, Integer> {
	
	@Query(value = "select * from general_members gm join\r\n"
			+ "users u on gm.member_id = u.member_idf ", nativeQuery = true)
	List<GenralMember> getAllGenralMember();
}
