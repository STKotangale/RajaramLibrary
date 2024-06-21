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

	@Query(value = "SELECT " + "is2.stock_id, " + "is2.memberIdF, " + "is2.invoiceNo, " + "is2.invoiceDate, "
			+ "is3.stockDetailId, " + "is3.book_idF AS bookId, " + "ib.bookName, " + "iscn.stockCopyId, "
			+ "au.username, " + "ibd.accessionNo, " + "ibd.bookdetailId, " + "ic.bookDays, " + "ic.finePerDays, "
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
			+ "agm.memberId = :memberId " + "AND is2.stock_type = 'A2' " + "AND is3.stock_type = 'A2' "
			+ "AND iscn.stock_type = 'A2' " + "AND ibd.bookIssue = 'N' "
			+ "AND is2.invoiceDate <= STR_TO_DATE(:returnDate, '%d-%m-%Y')", nativeQuery = true)
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

	@Query(value = "SELECT \r\n" + "    ibd.purchaseCopyNo, \r\n" + "    ibd.accessionNo, \r\n"
			+ "    ibd.bookDetailId, \r\n" + "    is3.*, \r\n" + "    ib.bookName, \r\n" + "    is2.*, \r\n"
			+ "    au.username \r\n" + "FROM \r\n" + "    invt_stock is2\r\n" + "LEFT JOIN \r\n"
			+ "    invt_stockdetail is3 ON is3.stock_idF = is2.stock_id AND is3.stock_type = 'A5'\r\n"
			+ "LEFT JOIN \r\n" + "    invt_book ib ON ib.bookId = is3.book_idF\r\n" + "LEFT JOIN \r\n"
			+ "    acc_ledger al ON al.ledgerID = is2.ledgerIDF\r\n" + "LEFT JOIN \r\n"
			+ "    invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId\r\n" + "LEFT JOIN \r\n"
			+ "    invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF\r\n" + "LEFT JOIN \r\n"
			+ "    auth_general_members agm ON agm.memberId = is2.memberIdF\r\n" + "LEFT JOIN \r\n"
			+ "    auth_users au ON au.memberIdF = agm.memberId\r\n" + "WHERE \r\n" + "    is2.stock_type = 'A5';\r\n"
			+ "", nativeQuery = true)
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

	@Query(value = "SELECT is2.stock_id, is2.invoiceNo, is2.invoiceDate, au.username, agm.firstName, agm.middleName, agm.lastName\r\n"
			+ "FROM invt_stock is2\r\n"
			+ "JOIN auth_general_members agm ON agm.memberId = is2.memberIdF\r\n"
			+ "JOIN auth_users au ON au.memberIdF = agm.memberId\r\n"
			+ "WHERE is2.stock_type = 'A2'\r\n"
			+ "ORDER BY is2.invoiceDate;\r\n"
			+ "", nativeQuery = true)
	List<BookIssue> getAllIssue();

	@Query(value = "SELECT " + "is2.stock_id AS stockId, " + "is2.invoiceNo AS action, " + "is2.invoiceDate AS date, "
			+ "au.username AS user, " + "agm.firstname AS firstName, " + "agm.middlename AS middleName, "
			+ "agm.lastname AS lastName, " + "CONCAT('[', GROUP_CONCAT(" + "   CONCAT("
			+ "       '{\"bookName\":\"', ib.bookName, '\",', '\"accessionNo\":\"', ibd.accessionNo, '\",', '\"bookDetailId\":', ibd.bookDetailId, '}'"
			+ "   )" + "   ORDER BY ibd.bookDetailId SEPARATOR ','" + "), ']') AS books " + "FROM invt_stock is2 "
			+ "JOIN auth_general_members agm ON agm.memberId = is2.memberIdF "
			+ "JOIN auth_users au ON au.memberIdF = agm.memberId "
			+ "JOIN invt_stockdetail is3 ON is3.stock_idF = is2.stock_id "
			+ "JOIN invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId "
			+ "JOIN invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF "
			+ "JOIN invt_book ib ON ib.bookId = is3.book_idF "
			+ "WHERE is2.stock_type = 'A2' AND is2.stock_id = :stockId "
			+ "GROUP BY is2.stock_id, is2.invoiceNo, is2.invoiceDate, au.username, agm.firstname, agm.middlename, agm.lastname", nativeQuery = true)
	List<Object[]> findIssueDetailsById(@Param("stockId") Integer stockId);

	@Query(value = "SELECT\r\n" + "    is2.stock_id,\r\n" + "    is2.invoiceNo,\r\n" + "    is2.invoiceDate,\r\n"
			+ "    au.username,\r\n" + "    CONCAT('[', GROUP_CONCAT(\r\n" + "        '{',\r\n"
			+ "        '\"bookId\": ', ib.bookId,\r\n" + "        ', \"fineDays\": ', is3.fineDays,\r\n"
			+ "        ', \"issuedate\": \"', is3.ref_issue_date, '\"',\r\n"
			+ "        ', \"fineAmount\": ', is3.fineAmount,\r\n"
			+ "        ', \"finePerDays\": ', is3.finePerDays,\r\n"
			+ "        ', \"bookDetailIds\": ', ibd.bookDetailId,\r\n"
			+ "        ', \"stockDetailId\": ', is3.stockDetailId,\r\n"
			+ "        ', \"AcessionNo\": ', ibd.accessionNo,\r\n"
			+ "        ', \"BookName\": \"', ib.bookName, '\"',\r\n" + "        '}'\r\n" + "        SEPARATOR ','\r\n"
			+ "    ), ']') AS bookDetailsList\r\n" + "FROM\r\n" + "    invt_stock is2\r\n" + "JOIN\r\n"
			+ "    auth_general_members agm ON agm.memberId = is2.memberIdF\r\n" + "JOIN\r\n"
			+ "    auth_users au ON au.memberIdF = agm.memberId\r\n" + "JOIN\r\n"
			+ "    invt_stockdetail is3 ON is3.stock_idF = is2.stock_id\r\n" + "JOIN\r\n"
			+ "    invt_book ib ON ib.bookId = is3.book_idF\r\n" + "JOIN\r\n"
			+ "    invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId\r\n" + "JOIN\r\n"
			+ "    invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF\r\n" + "WHERE\r\n"
			+ "    is2.stock_type = 'A3' AND is3.stock_type = 'A3'\r\n" + "GROUP BY\r\n"
			+ "    is2.stock_id, is2.invoiceNo, is2.invoiceDate, au.username;\r\n" + "", nativeQuery = true)
	List<Map<String, Object>> getStockDetailsWithBookDetails();
	
	@Query(value = "SELECT invoiceNo FROM invt_stock WHERE stock_type = 'A1' ORDER BY invoiceNo DESC LIMIT 1", nativeQuery = true)
    String findLatestPurchaseNo();

    @Query(value = "SELECT invoiceNo FROM invt_stock WHERE stock_type = 'A2' ORDER BY invoiceNo DESC LIMIT 1", nativeQuery = true)
    String findLatestIssueNo();

    @Query(value = "SELECT invoiceNo FROM invt_stock WHERE stock_type = 'A3' ORDER BY invoiceNo DESC LIMIT 1", nativeQuery = true)
    String findLatestIssueReturnNo();

    @Query(value = "SELECT invoiceNo FROM invt_stock WHERE stock_type = 'A4' ORDER BY invoiceNo DESC LIMIT 1", nativeQuery = true)
    String findLatestPurchaseReturnNo();

    @Query(value = "SELECT invoiceNo FROM invt_stock WHERE stock_type = 'A5' ORDER BY invoiceNo DESC LIMIT 1", nativeQuery = true)
    String findLatestBookLostNo();

    @Query(value = "SELECT invoiceNo FROM invt_stock WHERE stock_type = 'A6' ORDER BY invoiceNo DESC LIMIT 1", nativeQuery = true)
    String findLatestBookScrapNo();

	
}
