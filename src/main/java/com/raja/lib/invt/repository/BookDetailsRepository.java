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
public interface BookDetailsRepository extends JpaRepository<BookDetails, Integer> {

	@Query(value = "select ibd.bookDetailId , ibd.purchaseCopyNo , ib.bookName , is2.book_rate   from invt_book_details ibd left join\r\n"
			+ "invt_book ib on ib.bookId = ibd.bookIdF left join \r\n"
			+ "invt_stockdetail is2  on is2.stockDetailId = ibd.stockDetailIdF \r\n"
			+ "where ibd.isbn IS null; ", nativeQuery = true)
	List<BookDetail> findBooksByNullIsbn();

	@Query(value = "select ibd.bookDetailId, ibd.purchaseCopyNo, ib.bookName, is2.book_rate from invt_book_details ibd left join "
			+ "invt_book ib on ib.bookId = ibd.bookIdF left join "
			+ "invt_stockdetail is2 on is2.stockDetailId = ibd.stockDetailIdF", nativeQuery = true)
	List<BookDetail> findBooksDetail();

}
