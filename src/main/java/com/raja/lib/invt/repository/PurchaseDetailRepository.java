package com.raja.lib.invt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.raja.lib.invt.model.PurchaseDetail;
import com.raja.lib.invt.objects.BookName;
import com.raja.lib.invt.objects.BookRate;

@Repository
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Long> {

	@Query(value = "SELECT p.rate FROM purchase_detail AS p WHERE p.book_name = :bookName LIMIT 1", nativeQuery = true)
	BookRate getBookRate(@Param("bookName") String bookName);

	@Query(value = "SELECT DISTINCT book_name FROM purchase_detail;", nativeQuery = true)
	List<BookName> getBookName();

}