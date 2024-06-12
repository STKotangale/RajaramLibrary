package com.raja.lib.invt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.model.StockDetail;
import com.raja.lib.invt.objects.BookDetail;
import com.raja.lib.invt.objects.BookDetailNameCopyNO;
import com.raja.lib.invt.objects.BookDetailNameWithCopyNO;

@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Integer> {

	@Query(value = "SELECT ibd.*,\r\n" + "       ib.bookName,\r\n" + "       is2.book_rate,\r\n"
			+ "       CASE WHEN ibd.accessionNo IS NULL THEN 0 ELSE 1 END AS status\r\n"
			+ "FROM invt_book_details ibd\r\n" + "LEFT JOIN invt_book ib ON ib.bookId = ibd.bookIdF\r\n"
			+ "LEFT JOIN invt_stockdetail is2 ON is2.stockDetailId = ibd.stockDetailIdF;\r\n" + "", nativeQuery = true)
	List<BookDetail> findBooksByNullIsbn();

	@Query(value = "SELECT ib.bookId,\r\n" + "       ib.bookName,\r\n"
			+ "       GROUP_CONCAT(CONCAT(ibd.bookDetailId, ':', ibd.accessionNo, '-', ibd.purchaseCopyNo) ORDER BY ibd.purchaseCopyNo ASC) AS purchaseCopyNos\r\n"
			+ "FROM invt_book_details ibd\r\n" + "JOIN invt_book ib ON ibd.bookIdF = ib.bookId\r\n"
			+ "WHERE ibd.accessionNo IS NOT NULL\r\n" + "  AND ibd.bookIssue = 'Y'\r\n"
			+ "  AND ibd.bookWorkingStart = 'Y'\r\n" + "  AND ibd.bookLost = 'N'\r\n" + "  AND ibd.bookScrap = 'N'\r\n"
			+ "  AND ibd.book_return ='N'\r\n" + "  AND ibd.bookScrap ='N'\r\n" + "  AND ibd.copyNo IS NOT NULL\r\n"
			+ "GROUP BY ib.bookId, ib.bookName;\r\n" + "", nativeQuery = true)
	List<BookDetailNameCopyNO> findBooksDetail();

	@Query(value = "SELECT sdet.book_rate AS bookRate, " + "bk.bookName AS bookName, "
			+ "bdet.purchaseCopyNo AS purchaseCopyNo, " + "bdet.accessionNo AS accessionNo, "
			+ "bdet.bookDetailId AS bookDetailId " + "FROM invt_book_details bdet "
			+ "JOIN invt_stockdetail sdet ON bdet.stockDetailIdF = sdet.stockDetailId "
			+ "JOIN invt_book bk ON bk.bookId = bdet.bookIdF " + "WHERE bk.bookName = :bookName "
			+ "AND bdet.bookLost ='N' " + "AND bdet.bookScrap ='N' " + "AND bdet.book_return ='N'", nativeQuery = true)
	List<BookDetailNameWithCopyNO> findBookDetailsByBookName(@Param("bookName") String bookName);

	@Query("SELECT MAX(b.purchaseCopyNo) FROM BookDetails b WHERE b.bookIdF.bookId = :bookId")
	Integer findMaxCopyNoByBookId(@Param("bookId") int bookId);

}
