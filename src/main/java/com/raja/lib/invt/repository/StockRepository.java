package com.raja.lib.invt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

	@Query(value = "SELECT " + "JSON_OBJECT(" + "'stock_id', is2.stock_id, " + "'invoiceNo', is2.invoiceNo, "
			+ "'memberIdF', is2.memberIdF, " + "'memberName', au.username, " + "'invoiceDate', is2.invoiceDate, "
			+ "'bookDetails', JSON_ARRAYAGG(" + "JSON_OBJECT(" + "'bookId', ib.bookId, " + "'bookName', ib.bookName, "
			+ "'purchaseCopyNo', ibd.purchaseCopyNo" + ")" + ") " + ") AS json_response " + "FROM " + "invt_stock is2 "
			+ "JOIN " + "auth_users au ON au.memberIdF = is2.memberIdF " + "JOIN "
			+ "invt_stockdetail is3 ON is3.stock_idF = is2.stock_id " + "JOIN "
			+ "invt_book ib ON ib.bookId = is3.book_idF " + "JOIN "
			+ "invt_stock_copy_no isc ON isc.stockDetailIdF = is3.stockDetailId " + "JOIN "
			+ "invt_book_details ibd ON ibd.bookDetailId = isc.bookDetailIdF " + "GROUP BY "
			+ "is2.stock_id, is2.invoiceNo, is2.memberIdF, au.username, is2.invoiceDate", nativeQuery = true)
	List<String> getStockDetailsAsJson();
	

}
