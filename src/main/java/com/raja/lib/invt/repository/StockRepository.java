package com.raja.lib.invt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.Stock;
import com.raja.lib.invt.objects.GetIssueDetilsByUser;
import com.raja.lib.invt.resposne.CreateStockResponse;

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

	@Query(value = "SELECT is2.stock_id, " + "is2.memberIdF, " + "is2.invoiceNo, " + "is2.invoiceDate, "
			+ "is3.stockDetailId, " + "is3.book_idF as bookId, " + "ib.bookName, " + "iscn.stockCopyId, "
			+ "au.username, " + "ibd.* " + "FROM invt_stock is2 "
			+ "JOIN invt_stockdetail is3 ON is3.stock_idF = is2.stock_id "
			+ "JOIN invt_book ib ON ib.bookId = is3.book_idF "
			+ "JOIN invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId "
			+ "JOIN invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF "
			+ "JOIN auth_general_members agm ON agm.memberId = is2.memberIdF "
			+ "JOIN auth_users au ON au.memberIdF = agm.memberId " + "WHERE au.username = :username "
			+ "AND is2.stock_type = 'A2' " + "AND is3.stock_type = 'A2' "
			+ "AND iscn.stock_type = 'A2'", nativeQuery = true)
	List<GetIssueDetilsByUser> findStockDetailsByUsername(String username);

	@Query(value = "SELECT \r\n" + "    is2.stock_id, \r\n" + "    is2.invoiceNo, \r\n" + "    is2.memberIdF ,\r\n"
			+ "    is2.invoiceDate, \r\n" + "    is3.stockDetailId, \r\n" + "    is3.book_idF as bookId, \r\n"
			+ "    ib.bookName, \r\n" + "    iscn.stockCopyId, \r\n" + "    au.username, \r\n" + "    ibd.* \r\n"
			+ "FROM \r\n" + "    invt_stock is2 \r\n" + "JOIN \r\n"
			+ "    invt_stockdetail is3 ON is3.stock_idF = is2.stock_id \r\n" + "JOIN \r\n"
			+ "    invt_book ib ON ib.bookId = is3.book_idF \r\n" + "JOIN\r\n"
			+ "    invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId \r\n" + "JOIN \r\n"
			+ "    invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF \r\n" + "JOIN \r\n"
			+ "    auth_general_members agm ON agm.memberId = is2.memberIdF \r\n" + "JOIN \r\n"
			+ "    auth_users au ON au.memberIdF = agm.memberId \r\n" + "WHERE \r\n" + "    is2.stock_type = 'A3'\r\n"
			+ "    AND is3.stock_type = 'A3'\r\n" + "    AND iscn.stock_type = 'A3';\r\n" + "", nativeQuery = true)
	List<GetIssueDetilsByUser> findAllIssueReturn();

	@Query(value = "SELECT is2.stock_id AS stockId, is2.ledgerIDF AS ledgerId, is2.invoiceNo, is2.invoiceDate, JSON_OBJECTAGG(is3.stockDetailId, JSON_OBJECT('bookId', is3.book_idF, 'bookRate', is3.book_rate, 'bookAmount', is3.book_amount)) AS details FROM invt_stock is2 JOIN invt_stockdetail is3 ON is3.stock_idF = is2.stock_id WHERE is2.ledgerIDF IS NOT NULL GROUP BY is2.stock_id, is2.ledgerIDF, is2.invoiceNo, is2.invoiceDate", nativeQuery = true)
	List<CreateStockResponse> getStockDetails();

	@Query(value = "SELECT is2.stock_id, is2.ledgerIDF, is2.invoiceNo, is2.invoiceDate, "
			+ "is3.stockDetailId, is3.book_idF, is3.book_rate, is3.book_amount, "
			+ "ib.bookId, ib.bookName, ibd.purchaseCopyNo " + "FROM invt_stock AS is2 "
			+ "JOIN invt_stockdetail AS is3 ON is3.stock_idF = is2.stock_id "
			+ "JOIN acc_ledger AS al ON al.ledgerID = is2.ledgerIDF "
			+ "JOIN invt_book AS ib ON ib.bookId = is3.book_idF "
			+ "JOIN invt_stock_copy_no AS iscn ON iscn.stockDetailIdF = is3.stockDetailId "
			+ "JOIN invt_book_details AS ibd ON ibd.bookDetailId = iscn.bookDetailIdF "
			+ "WHERE al.ledgerName = :ledgerName", nativeQuery = true)
	List<Object[]> findStockDetailsByLedgerName(@Param("ledgerName") String ledgerName);

}
