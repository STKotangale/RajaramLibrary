package com.raja.lib.invt.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.Stock;
import com.raja.lib.invt.objects.AcessionForLostScarap;
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

	@Query(value = "SELECT " + "    is2.stock_id AS stockId, " + "    is2.invoiceNo, " + "    is2.invoiceDate, "
			+ "    al.ledgerName, " + "    GROUP_CONCAT(" + "        CONCAT("
			+ "            '{\"bookName\":\"', ib.bookName, '\",\"accessionNo\":\"', ibd.accessionNo, '\",\"stockDetailId\":', is3.stockDetailId, ',\"book_amount\":', is3.book_amount, '}'"
			+ "        ) " + "        ORDER BY is3.stockDetailId SEPARATOR ','" + "    ) AS books, "
			+ "    is2.billTotal, " + "    is2.discountPercent, " + "    is2.totalAfterDiscount, "
			+ "    is2.grandTotal " + "FROM " + "    invt_stock is2 " + "JOIN "
			+ "    invt_stockdetail is3 ON is3.stock_idF = is2.stock_id " + "JOIN "
			+ "    invt_book ib ON ib.bookId = is3.book_idF " + "JOIN "
			+ "    acc_ledger al ON al.ledgerID = is2.ledgerIDF " + "JOIN "
			+ "    invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId " + "JOIN "
			+ "    invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF " + "WHERE "
			+ "    is2.stock_type = 'A4' " + "    AND is3.stock_type = 'A4' "
			+ "    AND STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y') BETWEEN STR_TO_DATE(:startDate, '%d-%m-%Y') AND STR_TO_DATE(:endDate, '%d-%m-%Y') "
			+ "GROUP BY " + "    is2.stock_id, " + "    is2.invoiceNo, " + "    is2.invoiceDate, "
			+ "    al.ledgerName, " + "    is2.billTotal, " + "    is2.discountPercent, "
			+ "    is2.totalAfterDiscount, " + "    is2.grandTotal " + "ORDER BY "
			+ "    STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y');", nativeQuery = true)
	List<Map<String, Object>> findStockDetailsByType(@Param("startDate") String startDate,
			@Param("endDate") String endDate);

	@Query(value = "SELECT \n" + "    is2.stock_id AS stockId, \n" + "    is2.invoiceNo AS invoiceNo, \n"
			+ "    is2.invoiceDate AS invoiceDate, \n" + "    is2.billTotal AS billTotal, \n"
			+ "    is2.grandTotal AS grandTotal, \n" + "    is2.discountPercent AS discountPercent, \n"
			+ "    is2.discountAmount AS discountAmount, \n" + "    is2.gstPercent AS gstPercent, \n"
			+ "    is2.gstAmount AS gstAmount, \n" + "    is2.totalAfterDiscount AS totalAfterDiscount, \n"
			+ "    agm.memberId AS memberId, \n" + "    au.username AS username, \n"
			+ "    agm.firstName AS firstName, \n" + "    agm.middleName AS middleName, \n"
			+ "    agm.lastName AS lastName, \n" + "    JSON_ARRAYAGG( \n" + "        JSON_OBJECT( \n"
			+ "            'purchaseCopyNo', ibd.purchaseCopyNo, \n" + "            'accessionNo', ibd.accessionNo, \n"
			+ "            'bookDetailId', ibd.bookDetailId, \n" + "            'bookName', ib.bookName, \n"
			+ "            'book_amount', is3.book_amount \n" + "        ) \n" + "    ) AS bookDetails \n" + "FROM \n"
			+ "    invt_stock is2 \n" + "LEFT JOIN \n"
			+ "    invt_stockdetail is3 ON is3.stock_idF = is2.stock_id AND is3.stock_type = 'A5' \n" + "LEFT JOIN \n"
			+ "    invt_book ib ON ib.bookId = is3.book_idF \n" + "LEFT JOIN \n"
			+ "    acc_ledger al ON al.ledgerID = is2.ledgerIDF \n" + "LEFT JOIN \n"
			+ "    invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId \n" + "LEFT JOIN \n"
			+ "    invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF \n" + "LEFT JOIN \n"
			+ "    auth_general_members agm ON agm.memberId = is2.memberIdF \n" + "LEFT JOIN \n"
			+ "    auth_users au ON au.memberIdF = agm.memberId \n" + "WHERE \n" + "    is2.stock_type = 'A5' \n"
			+ "    AND STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y') BETWEEN STR_TO_DATE(:startDate, '%d-%m-%Y') AND STR_TO_DATE(:endDate, '%d-%m-%Y') \n"
			+ "GROUP BY \n" + "    is2.stock_id \n" + "ORDER BY \n"
			+ "    STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y');", nativeQuery = true)
	List<Map<String, Object>> findBookLost(@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query(value = "SELECT is2.stock_id AS stockId, " + "is2.invoiceNo, " + "is2.invoiceDate, " + "al.ledgerName, "
			+ "is2.billTotal, " + "is2.discountPercent, " + "is2.totalAfterDiscount, " + "is2.grandTotal, "
			+ "CONCAT('[', GROUP_CONCAT(CONCAT('{', " + "'\"bookDetailId\": ', ibd.bookDetailId, ', ', "
			+ "'\"accessionNo\": \"', ibd.accessionNo, '\", ', " + "'\"bookName\": \"', ib.bookName, '\", ', "
			+ "'\"amount\": ', is3.book_rate, ', ', "
			+ "'\"stockIdF\": ', is3.stock_idF, '}') SEPARATOR ','), ']') AS bookDetails " + "FROM invt_stock is2 "
			+ "JOIN invt_stockdetail is3 ON is3.stock_idF = is2.stock_id "
			+ "JOIN invt_book ib ON ib.bookId = is3.book_idF " + "JOIN acc_ledger al ON al.ledgerID = is2.ledgerIDF "
			+ "JOIN invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId "
			+ "JOIN invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF " + "WHERE is2.stock_type = 'A6' "
			+ "AND is3.stock_type = 'A6' "
			+ "AND STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y') BETWEEN STR_TO_DATE(:startDate, '%d-%m-%Y') AND STR_TO_DATE(:endDate, '%d-%m-%Y') "
			+ "GROUP BY is2.stock_id, is2.invoiceNo, is2.invoiceDate, al.ledgerName, is2.billTotal, "
			+ "is2.discountPercent, is2.totalAfterDiscount, is2.grandTotal "
			+ "ORDER BY STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y')", nativeQuery = true)
	List<Map<String, Object>> findBookScrap(@Param("startDate") String startDate, @Param("endDate") String endDate);

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

	@Query(value = "SELECT is2.stock_id, is2.invoiceNo, is2.invoiceDate, au.username, agm.firstName, agm.middleName, agm.lastName "
			+ "FROM invt_stock is2 " + "JOIN auth_general_members agm ON agm.memberId = is2.memberIdF "
			+ "JOIN auth_users au ON au.memberIdF = agm.memberId " + "WHERE is2.stock_type = 'A2' "
			+ "AND STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y') BETWEEN STR_TO_DATE(:startDate, '%d-%m-%Y') AND STR_TO_DATE(:endDate, '%d-%m-%Y') "
			+ "ORDER BY STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y')", nativeQuery = true)
	List<BookIssue> getAllIssue(@Param("startDate") String startDate, @Param("endDate") String endDate);

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

	@Query(value = "SELECT " + "    is2.stock_id, " + "    is2.invoiceNo, " + "    is2.invoiceDate, "
			+ "    au.username, " + "    CONCAT('[', GROUP_CONCAT( " + "        CONCAT( " + "            '{', "
			+ "            '\"bookId\": ', ib.bookId, ', ', " + "            '\"fineDays\": ', is3.fineDays, ', ', "
			+ "            '\"issuedate\": \"', is3.ref_issue_date, '\"', ', ', "
			+ "            '\"fineAmount\": ', is3.fineAmount, ', ', "
			+ "            '\"finePerDays\": ', is3.finePerDays, ', ', "
			+ "            '\"bookDetailIds\": ', ibd.bookDetailId, ', ', "
			+ "            '\"stockDetailId\": ', is3.stockDetailId, ', ', "
			+ "            '\"AcessionNo\": ', ibd.accessionNo, ', ', "
			+ "            '\"BookName\": \"', ib.bookName, '\"', " + "            '}' " + "        ) SEPARATOR ',' "
			+ "    ), ']') AS bookDetailsList " + "FROM " + "    invt_stock is2 " + "JOIN "
			+ "    auth_general_members agm ON agm.memberId = is2.memberIdF " + "JOIN "
			+ "    auth_users au ON au.memberIdF = agm.memberId " + "JOIN "
			+ "    invt_stockdetail is3 ON is3.stock_idF = is2.stock_id " + "JOIN "
			+ "    invt_book ib ON ib.bookId = is3.book_idF " + "JOIN "
			+ "    invt_stock_copy_no iscn ON iscn.stockDetailIdF = is3.stockDetailId " + "JOIN "
			+ "    invt_book_details ibd ON ibd.bookDetailId = iscn.bookDetailIdF " + "WHERE "
			+ "    is2.stock_type = 'A3' " + "    AND is3.stock_type = 'A3' "
			+ "    AND STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y') BETWEEN STR_TO_DATE(:startDate, '%d-%m-%Y') AND STR_TO_DATE(:endDate, '%d-%m-%Y') "
			+ "GROUP BY " + "    is2.stock_id, is2.invoiceNo, is2.invoiceDate, au.username " + "ORDER BY "
			+ "    STR_TO_DATE(is2.invoiceDate, '%d-%m-%Y')", nativeQuery = true)
	List<Map<String, Object>> getStockDetailsWithBookDetails(@Param("startDate") String startDate,
			@Param("endDate") String endDate);

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

	@Query(value = "SELECT \r\n" + "    ibd.bookDetailId,\r\n" + "    ibd.accessionNo,\r\n" + "    ib.bookId,\r\n"
			+ "    ib.bookName,\r\n" + "    stdet.book_rate\r\n" + "FROM \r\n" + "    invt_book_details ibd\r\n"
			+ "JOIN \r\n" + "    invt_book ib ON ib.bookId = ibd.bookIdF\r\n" + "JOIN \r\n"
			+ "    invt_stockdetail stdet ON stdet.stockDetailId = ibd.stockDetailIdF\r\n" + "WHERE \r\n"
			+ "    ibd.bookLost = 'N' \r\n" + "    AND ibd.book_return = 'N' \r\n" + "    AND ibd.bookScrap = 'N'\r\n"
			+ "ORDER BY \r\n" + "    ib.bookName;\r\n" + "", nativeQuery = true)
	List<AcessionForLostScarap> GetAccesionNoForTransactions();

}
