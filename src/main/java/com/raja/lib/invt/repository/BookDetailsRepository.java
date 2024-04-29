package com.raja.lib.invt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.model.PurchaseDetail;
import com.raja.lib.invt.objects.BookDetail;

import jakarta.transaction.Transactional;

@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {

	@Query(value = "SELECT \r\n"
			+ "    bd.book_detail_id AS id,\r\n"
			+ "    bd.purchase_copy_no,\r\n"
			+ "    bd.rate,\r\n"
			+ "    bd.author ,\r\n"
			+ "    b.book_name  AS book_name\r\n"
			+ "FROM \r\n"
			+ "    book_details bd\r\n"
			+ "JOIN \r\n"
			+ "    purchase_detail pd ON bd.book_name = pd.purchase_detail_id\r\n"
			+ "JOIN \r\n"
			+ "    book b ON pd.book_idf = b.book_id\r\n"
			+ "WHERE \r\n"
			+ "    bd.isbn IS NULL;\r\n"
			+ ""
			+ "", nativeQuery = true)
	List<BookDetail> findBooksByNullIsbn();

	@Transactional
    @Modifying
    @Query("DELETE FROM BookDetails bd WHERE bd.purchaseDetail = :purchaseDetail")
    void deleteByPurchaseDetail(@Param("purchaseDetail") PurchaseDetail purchaseDetail);
}
