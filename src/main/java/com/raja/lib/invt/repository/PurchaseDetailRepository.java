package com.raja.lib.invt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.Purchase;
import com.raja.lib.invt.model.PurchaseDetail;
import com.raja.lib.invt.objects.BookName;
import com.raja.lib.invt.objects.BookRate;

import jakarta.transaction.Transactional;

@Repository
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Long> {

//	@Query(value = "SELECT p.rate FROM purchase_detail AS p WHERE p.book_name = :bookName LIMIT 1", nativeQuery = true)
//	BookRate getBookRate(@Param("bookName") String bookName);
//
//	@Query(value = "SELECT DISTINCT book_name FROM purchase_detail;", nativeQuery = true)
//	List<BookName> getBookName();
//	
	 @Query(value="SELECT MAX(ipd.srno) FROM invt_purchase_detail ipd ",nativeQuery = true)
	    Integer findMaxSrno();
	 
	 @Modifying
	    @Query("DELETE FROM PurchaseDetail pd WHERE pd.purchase = :purchase")
	    void deleteByPurchase(@Param("purchase") Purchase purchase);

}
