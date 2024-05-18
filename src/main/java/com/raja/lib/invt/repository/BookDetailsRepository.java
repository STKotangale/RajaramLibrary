package com.raja.lib.invt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.objects.BookDetail;
import com.raja.lib.invt.objects.BookDetailNameCopyNO;


@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Integer> {

	@Query(value = "select ibd.bookDetailId , ibd.purchaseCopyNo , ib.bookName , is2.book_rate   from invt_book_details ibd left join\r\n"
			+ "invt_book ib on ib.bookId = ibd.bookIdF left join \r\n"
			+ "invt_stockdetail is2  on is2.stockDetailId = ibd.stockDetailIdF \r\n"
			+ "where ibd.isbn IS null; ", nativeQuery = true)
	List<BookDetail> findBooksByNullIsbn();

	

	@Query(value = "SELECT ib.bookId, ib.bookName, GROUP_CONCAT(CONCAT(ibd.bookDetailId, ':', ibd.purchaseCopyNo) ORDER BY ibd.purchaseCopyNo ASC) AS purchaseCopyNos FROM invt_book_details ibd JOIN invt_book ib ON ibd.bookIdF = ib.bookId WHERE ibd.isbn IS NOT NULL AND ibd.bookIssue = 'Y' AND ibd.bookWorkingStart = 'Y' AND ibd.bookLostScrap = 'N' AND ibd.copyNo IS NOT NULL GROUP BY ib.bookId, ib.bookName", nativeQuery = true)
	List<BookDetailNameCopyNO> findBooksDetail();



}
