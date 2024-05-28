package com.raja.lib.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raja.lib.auth.model.GeneralMember;
import com.raja.lib.auth.objects.GenralMember;
import com.raja.lib.auth.objects.MemberBookInfo;

@Repository
public interface GeneralMemberRepository extends JpaRepository<GeneralMember, Integer> {

	@Query(value = "select *from auth_general_members agm join\r\n"
			+ "auth_users au on au.memberIdF = agm.memberId ; ", nativeQuery = true)
	List<GenralMember> getAllGenralMember();

	@Query(value = "SELECT * FROM auth_general_members agm JOIN auth_users au ON au.memberIdF = agm.memberId WHERE agm.memberId = :memberId", nativeQuery = true)
	GenralMember getGeneralMemberById(@Param("memberId") int memberId);

	@Query(value = "SELECT " + "agm.memberId AS memberId, " + "ib.bookName AS bookName, "
			+ "ibd.purchaseCopyNo AS purchaseCopyNo, "
			+ "MAX(CASE WHEN is2.stock_type = 'A2' THEN is2.invoiceDate END) AS issueDate, "
			+ "MAX(CASE WHEN is2.stock_type = 'A3' THEN is2.invoiceDate END) AS returnDate " + "FROM invt_stock is2 "
			+ "JOIN invt_stockdetail is3 ON is3.stock_idF = is2.stock_id "
			+ "JOIN invt_book ib ON ib.bookId = is3.book_idF "
			+ "JOIN invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId "
			+ "JOIN invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF "
			+ "JOIN auth_general_members agm ON agm.memberId = is2.memberIdF "
			+ "JOIN auth_users au ON au.memberIdF = agm.memberId " + "WHERE au.username = :username "
			+ "GROUP BY agm.memberId, ib.bookName, ibd.purchaseCopyNo "
			+ "HAVING MAX(CASE WHEN is2.stock_type = 'A3' THEN is2.invoiceDate END) IS NULL", nativeQuery = true)
	List<MemberBookInfo> findMemberBookInfoByUsername(@Param("username") String username);

}
