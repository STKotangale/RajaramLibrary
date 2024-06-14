package com.raja.lib.invt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.model.StockCopyNo;
import com.raja.lib.invt.model.StockDetail;

public interface StockCopyNoRepository extends JpaRepository<StockCopyNo, Integer> {

	 @Query("SELECT sc FROM StockCopyNo sc WHERE sc.stockDetailIdF = :stockDetail AND sc.bookDetailIdF = :bookDetails")
	    StockCopyNo findByStockDetailIdFAndBookDetailIdF(@Param("stockDetail") StockDetail stockDetail, @Param("bookDetails") BookDetails bookDetails);
	 
	    Optional<StockCopyNo> findByBookDetailIdF(BookDetails bookDetailIdF);

}
