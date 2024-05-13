package com.raja.lib.invt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.objects.BookDetail;

import jakarta.transaction.Transactional;

@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {

	@Query(value = "SELECT \r\n"
			+ "    ibd.bookDetailId,\r\n"
			+ "    ibd.purchaseCopyNo,\r\n"
			+ "    ib.bookName,\r\n"
			+ "    ipd.book_rate \r\n"
			+ "FROM \r\n"
			+ "    invt_book_details ibd\r\n"
			+ "JOIN \r\n"
			+ "    invt_book ib ON ib.bookId = ibd.bookIdF\r\n"
			+ "LEFT JOIN \r\n"
			+ "    invt_purchase_detail ipd ON ipd.purchaseDetailId = ibd.purchaseDetailIdF \r\n"
			+ "WHERE \r\n"
			+ "    ibd.isbn IS NULL;\r\n"
			+ "", nativeQuery = true)
	List<BookDetail> findBooksByNullIsbn();

//	@Transactional
//    @Modifying
//    @Query("DELETE FROM BookDetails bd WHERE bd.purchaseDetail = :purchaseDetail")
//    void deleteByPurchaseDetail(@Param("purchaseDetail") PurchaseDetail purchaseDetail);
}
