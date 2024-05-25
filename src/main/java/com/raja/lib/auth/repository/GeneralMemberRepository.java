package com.raja.lib.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raja.lib.auth.model.GeneralMember;
import com.raja.lib.auth.objects.GenralMember;

@Repository
public interface GeneralMemberRepository extends JpaRepository<GeneralMember, Integer> {
	
	@Query(value = "select *from auth_general_members agm join\r\n"
			+ "auth_users au on au.memberIdF = agm.memberId ; ", nativeQuery = true)
	List<GenralMember> getAllGenralMember();
	
	
	@Query(value = "SELECT * FROM auth_general_members agm JOIN auth_users au ON au.memberIdF = agm.memberId WHERE agm.memberId = :memberId", nativeQuery = true)
	GenralMember getGeneralMemberById(@Param("memberId") int memberId);

	
}
