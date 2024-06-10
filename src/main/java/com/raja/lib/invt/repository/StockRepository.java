package com.raja.lib.invt.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.Stock;
import com.raja.lib.invt.objects.BookIssue;
import com.raja.lib.invt.objects.GetAllIssueBookDetailsByUsername;
import com.raja.lib.invt.objects.GetIssueDetilsByUser;
import com.raja.lib.invt.objects.InvoiceDateProjection;
import com.raja.lib.invt.objects.StockModel;
import com.raja.lib.invt.resposne.CreateStockResponse;
import com.raja.lib.invt.resposne.PurchaseReturnDTO;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

	@Query(value = "SELECT \r\n" + "    JSON_OBJECT(\r\n" + "        'stock_id', is2.stock_id,\r\n"
			+ "        'invoiceNo', is2.invoiceNo,\r\n" + "        'memberIdF', is2.memberIdF,\r\n"
			+ "        'memberName', au.username,\r\n" + "        'invoiceDate', is2.invoiceDate,\r\n"
			+ "        'bookDetails', JSON_ARRAYAGG(\r\n" + "            JSON_OBJECT(\r\n"
			+ "                'bookId', ib.bookId,\r\n" + "                'bookName', ib.bookName,\r\n"
			+ "                'purchaseCopyNo', ibd.purchaseCopyNo,\r\n"
			+ "                'AccessionNo', ibd.accessionNo -- Corrected spelling\r\n" + "            )\r\n"
			+ "        )\r\n" + "    ) AS json_response \r\n" + "FROM \r\n" + "    invt_stock is2\r\n" + "JOIN \r\n"
			+ "    auth_users au ON au.memberIdF = is2.memberIdF\r\n" + "JOIN \r\n"
			+ "    invt_stockdetail is3 ON is3.stock_idF = is2.stock_id\r\n" + "JOIN \r\n"
			+ "    invt_book ib ON ib.bookId = is3.book_idF\r\n" + "JOIN \r\n"
			+ "    invt_stock_copy_no isc ON isc.stockDetailIdF = is3.stockDetailId\r\n" + "JOIN \r\n"
			+ "    invt_book_details ibd ON ibd.bookDetailId = isc.bookDetailIdF\r\n" + "GROUP BY \r\n"
			+ "    is2.stock_id, is2.invoiceNo, is2.memberIdF, au.username, is2.invoiceDate\r\n"
			+ "", nativeQuery = true)
	List<String> getStockDetailsAsJson();

	@Query(value = "WITH DateCalculations AS (" + "SELECT " + "is2.stock_id, " + "is2.memberIdF, " + "is2.invoiceNo, "
			+ "is2.invoiceDate, " + "is3.stockDetailId, " + "is3.book_idF AS bookId, " + "ib.bookName, "
			+ "iscn.stockCopyId, " + "au.username, " + "ibd.accessionNo, " + "ibd.bookdetailId, " + // Added
																									// bookdetailId
			"ic.bookDays, " + "ic.finePerDays, "
			+ "DATEDIFF(STR_TO_DATE(:returnDate, '%d-%m-%Y'), STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y')) AS daysKept, "
			+ "CASE "
			+ "    WHEN DATEDIFF(STR_TO_DATE(:returnDate, '%d-%m-%Y'), STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y')) > ic.bookDays "
			+ "    THEN DATEDIFF(STR_TO_DATE(:returnDate, '%d-%m-%Y'), STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y')) - ic.bookDays "
			+ "    ELSE 0 " + "END AS fineDays, " + "CASE "
			+ "    WHEN DATEDIFF(STR_TO_DATE(:returnDate, '%d-%m-%Y'), STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y')) > ic.bookDays "
			+ "    THEN (DATEDIFF(STR_TO_DATE(:returnDate, '%d-%m-%Y'), STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y')) - ic.bookDays) * ic.finePerDays "
			+ "    ELSE 0 " + "END AS fineAmount " + "FROM " + "invt_stock is2 "
			+ "JOIN invt_stockdetail is3 ON is3.stock_idF = is2.stock_id "
			+ "JOIN invt_book ib ON ib.bookId = is3.book_idF "
			+ "JOIN invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId "
			+ "JOIN invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF "
			+ "JOIN auth_general_members agm ON agm.memberId = is2.memberIdF "
			+ "JOIN auth_users au ON au.memberIdF = agm.memberId " + "JOIN invt_config ic ON 1 = 1 " + "WHERE "
			+ "    agm.memberId = :memberId " + "    AND is2.stock_type = 'A2' " + "    AND is3.stock_type = 'A2' "
			+ "    AND iscn.stock_type = 'A2' " + "    AND ibd.bookIssue = 'N' "
			+ "    AND is2.invoiceDate <= STR_TO_DATE(:returnDate, '%d-%m-%Y')" + ") " + "SELECT " + "    stock_id, "
			+ "    memberIdF, " + "    invoiceNo, " + "    invoiceDate, " + "    stockDetailId, " + "    bookId, "
			+ "    bookName, " + "    stockCopyId, " + "    username, " + "    accessionNo, " + "    bookDays, "
			+ "    finePerDays, " + "    daysKept, " + "    fineDays, " + "    fineAmount, " + "    bookdetailId " + // Included
																														// bookdetailId
																														// in
																														// the
																														// SELECT
																														// statement
			"FROM " + "    DateCalculations", nativeQuery = true)
	List<GetAllIssueBookDetailsByUsername> findStockDetailsByUsernameAndReturnDate(@Param("memberId") int memberId,
			@Param("returnDate") String returnDate);

	@Query(value = "SELECT \r\n" + "    is2.stock_id, \r\n" + "    is2.invoiceNo, \r\n" + "    is2.fineDays,\r\n"
			+ "    is2.finePerDays ,\r\n" + "    is2.fineAmount,\r\n" + "    is2.memberIdF,\r\n"
			+ "    is2.invoiceDate, \r\n" + "    is3.stockDetailId, \r\n" + "    is3.book_idF as bookId, \r\n"
			+ "    ib.bookName, \r\n" + "    iscn.stockCopyId, \r\n" + "    au.username, \r\n" + "    ibd.* \r\n"
			+ "FROM \r\n" + "    invt_stock is2 \r\n" + "JOIN \r\n"
			+ "    invt_stockdetail is3 ON is3.stock_idF = is2.stock_id \r\n" + "JOIN \r\n"
			+ "    invt_book ib ON ib.bookId = is3.book_idF \r\n" + "JOIN \r\n"
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

	@Query(value = "SELECT \r\n" + "    al.ledgerName, \r\n" + "    ibd.purchaseCopyNo, \r\n"
			+ "    ibd.accessionNo,\r\n" + "    ibd.bookDetailId, \r\n" + "    is3.*, \r\n" + "    ib.bookName, \r\n"
			+ "    is2.* \r\n" + "FROM \r\n" + "    invt_stock is2 \r\n" + "JOIN \r\n"
			+ "    invt_stockdetail is3 ON is3.stock_idF = is2.stock_id \r\n" + "JOIN \r\n"
			+ "    invt_book ib ON ib.bookId = is3.book_idF \r\n" + "JOIN \r\n"
			+ "    acc_ledger al ON al.ledgerID = is2.ledgerIDF \r\n" + "JOIN \r\n"
			+ "    invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId \r\n" + "JOIN \r\n"
			+ "    invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF \r\n" + "WHERE \r\n"
			+ "    is2.stock_type = 'A4' \r\n" + "    AND is3.stock_type = 'A4'\r\n" + "", nativeQuery = true)
	List<PurchaseReturnDTO> findStockDetailsByType();

	@Query(value = "SELECT al.ledgerName, ibd.purchaseCopyNo,ibd.accessionNo ,ibd.bookDetailId, is3.*, ib.bookName, is2.*\r\n"
			+ "FROM invt_stock is2\r\n" + "JOIN invt_stockdetail is3 ON is3.stock_idF = is2.stock_id\r\n"
			+ "JOIN invt_book ib ON ib.bookId = is3.book_idF\r\n"
			+ "JOIN acc_ledger al ON al.ledgerID = is2.ledgerIDF\r\n"
			+ "JOIN invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId\r\n"
			+ "JOIN invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF\r\n"
			+ "WHERE is2.stock_type = 'A5'\r\n" + "AND is3.stock_type = 'A5';\r\n" + "", nativeQuery = true)
	List<PurchaseReturnDTO> findBookLost();

	@Query(value = "SELECT al.ledgerName, ibd.purchaseCopyNo, ibd.bookDetailId,ibd.accessionNo , is3.*, ib.bookName, is2.*\r\n"
			+ "FROM invt_stock is2\r\n" + "JOIN invt_stockdetail is3 ON is3.stock_idF = is2.stock_id\r\n"
			+ "JOIN invt_book ib ON ib.bookId = is3.book_idF\r\n"
			+ "JOIN acc_ledger al ON al.ledgerID = is2.ledgerIDF\r\n"
			+ "JOIN invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId\r\n"
			+ "JOIN invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF\r\n"
			+ "WHERE is2.stock_type = 'A6'\r\n" + "AND is3.stock_type = 'A6';\r\n" + "", nativeQuery = true)
	List<PurchaseReturnDTO> findBookScrap();

	@Query(value = "select invoiceDate  from invt_stock is2 join\r\n"
			+ "invt_stockdetail is3 on is3.stock_idF  = is2.stock_id join \r\n"
			+ "invt_stock_copy_no iscn on iscn.stockDetailIdF = is3.stockDetailId join \r\n"
			+ "invt_book_details ibd on ibd.bookDetailId = iscn.bookDetailIdF \r\n"
			+ "where ibd.bookDetailId= bookDetailId  and \r\n" + "is2.stock_type =\"A2\"", nativeQuery = true)
	List<InvoiceDateProjection> findInvoiceDateByBookDetailId(@Param("bookDetailId") Long bookDetailId);

	@Query(value = "SELECT is2.stock_id, is2.ledgerIDF, is2.invoiceNo, is2.invoiceDate, is2.grandTotal, al.ledgerName\r\n"
			+ "FROM invt_stock is2\r\n" + "JOIN acc_ledger al ON al.ledgerID = is2.ledgerIDF\r\n"
			+ "WHERE is2.stock_type = 'A1';\r\n" + "", nativeQuery = true)
	List<StockModel> getAllStock();

	@Query(value = "select is2.stock_id , is2.invoiceNo , is2.invoiceDate , au.username  from invt_stock is2 join\r\n"
			+ "auth_general_members agm ON agm.memberId = is2.memberIdF join \r\n"
			+ "auth_users au on au.memberIdF = agm.memberId \r\n" + "where is2.stock_type ='A2'", nativeQuery = true)
	List<BookIssue> getAllIssue();

	@Query(value = "SELECT is2.stock_id AS stockId, is2.invoiceNo AS action, is2.invoiceDate AS date, au.username AS user, JSON_ARRAYAGG(JSON_OBJECT('bookName', ib.bookName, 'accessionNo', ibd.accessionNo, 'bookDetailId', ibd.bookDetailId)) AS books "
			+ "FROM invt_stock is2 " + "JOIN auth_general_members agm ON agm.memberId = is2.memberIdF "
			+ "JOIN auth_users au ON au.memberIdF = agm.memberId "
			+ "JOIN invt_stockdetail is3 ON is3.stock_idF = is2.stock_id "
			+ "JOIN invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId "
			+ "JOIN invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF "
			+ "JOIN invt_book ib ON ib.bookId = is3.book_idF "
			+ "WHERE is2.stock_type = 'A2' AND is2.stock_id = :stockId "
			+ "GROUP BY is2.stock_id, is2.invoiceNo, is2.invoiceDate, au.username", nativeQuery = true)
	List<Object[]> findIssueDetailsById(@Param("stockId") Integer stockId);

	@Query(value = "SELECT " + "    is2.stock_id, " + "    is2.invoiceNo, " + "    is2.invoiceDate, "
			+ "    au.username, " + "    JSON_ARRAYAGG(" + "        JSON_OBJECT(" + "            'bookId', ib.bookId, "
			+ "            'fineDays', is3.fineDays, " + "            'issuedate', is3.ref_issue_date, "
			+ "            'fineAmount', is3.fineAmount, " + "            'finePerDays', is3.finePerDays, "
			+ "            'bookDetailIds', ibd.bookDetailId, " + "            'stockDetailId', is3.stockDetailId, "
			+ "            'AcessionNo', ibd.accessionNo, " + "            'BookName', ib.bookName" + "        )"
			+ "    ) AS bookDetailsList " + "FROM " + "    invt_stock is2 " + "JOIN "
			+ "    auth_general_members agm ON agm.memberId = is2.memberIdF " + "JOIN "
			+ "    auth_users au ON au.memberIdF = agm.memberId " + "JOIN "
			+ "    invt_stockdetail is3 ON is3.stock_idF = is2.stock_id " + "JOIN "
			+ "    invt_book ib ON ib.bookId = is3.book_idF " + "JOIN "
			+ "    invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId " + "JOIN "
			+ "    invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF " + "WHERE "
			+ "    is2.stock_type = 'A3' AND is3.stock_type = 'A3' " + "GROUP BY "
			+ "    is2.stock_id, is2.invoiceNo, is2.invoiceDate, au.username", nativeQuery = true)
	List<Map<String, Object>> getStockDetailsWithBookDetails();

}
