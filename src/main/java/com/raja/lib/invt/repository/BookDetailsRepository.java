package com.raja.lib.invt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.objects.BookDetail;

@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {

	@Query(value = "SELECT " + "    book_details.book_detail_id AS id, " + "    book_details.purchase_copy_no, "
			+ "    book_details.rate, " + "    purchase_detail.book_name " + "FROM " + "    book_details " + "JOIN "
			+ "    purchase_detail " + "ON " + "    book_details.book_name = purchase_detail.purchase_detail_id "
			+ "WHERE " + "    book_details.isbn IS NULL", nativeQuery = true)
	List<BookDetail> findBooksByNullIsbn();

}
