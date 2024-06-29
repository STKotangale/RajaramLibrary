package com.raja.lib.invt.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raja.lib.acc.model.Ledger;
import com.raja.lib.acc.repository.LedgerRepository;
import com.raja.lib.auth.model.GeneralMember;
import com.raja.lib.auth.repository.GeneralMemberRepository;
import com.raja.lib.auth.service.SessionService;
import com.raja.lib.invt.model.Book;
import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.model.Stock;
import com.raja.lib.invt.model.StockCopyNo;
import com.raja.lib.invt.model.StockDetail;
import com.raja.lib.invt.objects.AcessionForLostScarap;
import com.raja.lib.invt.objects.BookIssue;
import com.raja.lib.invt.objects.GetAllIssueBookDetailsByUsername;
import com.raja.lib.invt.objects.StockModel;
import com.raja.lib.invt.repository.BookDetailsRepository;
import com.raja.lib.invt.repository.BookRepository;
import com.raja.lib.invt.repository.StockCopyNoRepository;
import com.raja.lib.invt.repository.StockDetailRepository;
import com.raja.lib.invt.repository.StockRepository;
import com.raja.lib.invt.request.BookDetailDto;
import com.raja.lib.invt.request.BookDetailsDTO;
import com.raja.lib.invt.request.BookIssueRequestDto;
import com.raja.lib.invt.request.BookIssueReturnRequestDTO;
import com.raja.lib.invt.request.BookLostBookDetailDTO;
import com.raja.lib.invt.request.BookLostRequestDTO;
import com.raja.lib.invt.request.PurchaseReturnRequestDTO;
import com.raja.lib.invt.request.StockDetailRequestDTO;
import com.raja.lib.invt.request.StockRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.resposne.BookDetailss;
import com.raja.lib.invt.resposne.BookResponseDTO;
import com.raja.lib.invt.resposne.IssueDetailsDTO;
import com.raja.lib.invt.resposne.PurchaseReturnBookDetailDTO;
import com.raja.lib.invt.resposne.PurchaseReturnDTO;
import com.raja.lib.invt.resposne.StockDetailDTOs;
import com.raja.lib.invt.resposne.StockDetailResponseDTO;
import com.raja.lib.invt.resposne.StockInvoiceResponseDTO;
import com.raja.lib.invt.resposne.StockResponseDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class StockService {

	private final StockRepository stockRepository;
	private final StockDetailRepository stockDetailRepository;
	private final LedgerRepository ledgerRepository;
	private final BookRepository bookRepository;
	private final BookDetailsRepository bookDetailsRepository;
	private final GeneralMemberRepository generalMemberRepository;
	private final StockCopyNoRepository stockCopyNoRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

	
	@Autowired
    private SessionService sessionService;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	 @Autowired
	    private ObjectMapper objectMapper;
//------------------------------------------ Inovoice no--------------------------------------------------

	    public String getNextPurchaseNo() {
	        return incrementInvoiceNumber(stockRepository.findLatestPurchaseNo());
	    }

	    
	    public String getNextIssueNo() {
	        return incrementInvoiceNumber(stockRepository.findLatestIssueNo());
	    }

	    
	    public String getNextIssueReturnNo() {
	        return incrementInvoiceNumber(stockRepository.findLatestIssueReturnNo());
	    }

	    
	    public String getNextPurchaseReturnNo() {
	        return incrementInvoiceNumber(stockRepository.findLatestPurchaseReturnNo());
	    }

	    
	    public String getNextBookLostNo() {
	        return incrementInvoiceNumber(stockRepository.findLatestBookLostNo());
	    }

	    
	    public String getNextBookScrapNo() {
	        return incrementInvoiceNumber(stockRepository.findLatestBookScrapNo());
	    }

	    private String incrementInvoiceNumber(String latestInvoiceNumber) {
	        if (latestInvoiceNumber != null && !latestInvoiceNumber.isEmpty()) {
	            try {
	                int nextInvoiceNumber = Integer.parseInt(latestInvoiceNumber) + 1;
	                return String.valueOf(nextInvoiceNumber);
	            } catch (NumberFormatException e) {
	                e.printStackTrace();
	            }
	        }
	        return "1";
	    }
	    
//	    ----------------------------------------- stock api-------------------------------
	    
	@Transactional
	public ApiResponseDTO<Void> createStock(StockRequestDTO stockRequestDTO) {
		Ledger ledger = ledgerRepository.findById(stockRequestDTO.getLedgerIDF())
				.orElseThrow(() -> new RuntimeException("Ledger not found"));

		Stock stock = new Stock();
		stock.setStock_type("A1");
		stock.setInvoiceNo(stockRequestDTO.getInvoiceNo());
		stock.setInvoiceDate(stockRequestDTO.getInvoiceDate());
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		stock.setInvoice_time(LocalDateTime.now().format(timeFormatter));
		stock.setBillTotal(stockRequestDTO.getBillTotal());
		stock.setDiscountPercent(stockRequestDTO.getDiscountPercent());
		stock.setDiscountAmount(stockRequestDTO.getDiscountAmount());
		stock.setTotalAfterDiscount(stockRequestDTO.getTotalAfterDiscount());
		stock.setGstPercent(stockRequestDTO.getGstPercent());
		stock.setGstAmount(stockRequestDTO.getGstAmount());
		stock.setGrandTotal(stockRequestDTO.getGrandTotal());
		stock.setLedgerIDF(ledger);

		Integer maxSrno = stockDetailRepository.findMaxSrno();
		int nextSrno = (maxSrno == null ? 0 : maxSrno) + 1;

		List<StockDetail> stockDetails = new ArrayList<>();
		List<BookDetails> bookDetailsList = new ArrayList<>();

		for (StockDetailRequestDTO detailDTO : stockRequestDTO.getStockDetails()) {
			Book book = bookRepository.findById(detailDTO.getBookIdF())
					.orElseThrow(() -> new RuntimeException("Book not found"));

			StockDetail stockDetail = new StockDetail();
			stockDetail.setStockIdF(stock);
			stockDetail.setSrno(nextSrno++);
			stockDetail.setBook_qty(detailDTO.getBookQty());
			stockDetail.setBook_rate(detailDTO.getBookRate());
			stockDetail.setStock_type("A1");
			stockDetail.setBook_amount(detailDTO.getBookAmount());
			stockDetail.setBook_idF(book);
			stockDetails.add(stockDetail);

			List<BookDetails> generatedBookDetails = generateBookDetails(stockDetail, detailDTO.getBookQty());
			bookDetailsList.addAll(generatedBookDetails);
		}

		stock.setStockDetails(stockDetails);

		Stock savedStock = stockRepository.save(stock);
		bookDetailsRepository.saveAll(bookDetailsList);

		return new ApiResponseDTO<>(true, "Stock created successfully", null, HttpStatus.CREATED.value());
	}

	private List<BookDetails> generateBookDetails(StockDetail stockDetail, int quantity) {
		List<BookDetails> bookDetailsList = new ArrayList<>();

		Integer maxCopyNo = bookDetailsRepository.findMaxCopyNoByBookId(stockDetail.getBook_idF().getBookId());
		int purchaseCopyNo = (maxCopyNo == null ? 0 : maxCopyNo) + 1;

		for (int i = 0; i < quantity; i++) {
			BookDetails bookDetails = new BookDetails();
			bookDetails.setStockDetailIdF(stockDetail);
			bookDetails.setBookIdF(stockDetail.getBook_idF());
			bookDetails.setPurchaseCopyNo(purchaseCopyNo);

			bookDetailsList.add(bookDetails);

			purchaseCopyNo++;
		}
		return bookDetailsList;
	}

	public ApiResponseDTO<StockResponseDTO> getStockById(int stockId) {
		Stock stock = stockRepository.findById(stockId)
				.orElseThrow(() -> new RuntimeException("Stock not found with id: " + stockId));

		StockResponseDTO stockResponseDTO = mapToStockResponseDTO(stock);

		return new ApiResponseDTO<>(true, "Stock found", stockResponseDTO, HttpStatus.OK.value());
	}

//	------------------------------------------------------------------------------
	

	
	 @Transactional
	    public ApiResponseDTO<List<StockInvoiceResponseDTO>> getStockDetails(String startDate, String endDate) {
	        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate start = LocalDate.parse(startDate, inputFormatter);
	        LocalDate end = LocalDate.parse(endDate, inputFormatter);

	        int startYear = start.getYear();
	        int endYear = end.getYear();

	        try {
	            if (!sessionService.checkCurrentYear(startYear) || !sessionService.checkCurrentYear(endYear)) {
	                return new ApiResponseDTO<>(false, "Session not found for provided year range", null, HttpStatus.BAD_REQUEST.value());
	            }
	        } catch (Exception e) {
	            return new ApiResponseDTO<>(false, e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value());
	        }

	        LOGGER.info("Fetching stock details from {} to {}", startDate, endDate);

	        List<Object[]> results = fetchStockDetails(start.format(outputFormatter), end.format(outputFormatter));
	        Map<Integer, StockInvoiceResponseDTO> stockMap = new HashMap<>();

	        for (Object[] row : results) {
	            int stockId = (Integer) row[0];
	            StockInvoiceResponseDTO stockResponseDTO = stockMap.get(stockId);

	            if (stockResponseDTO == null) {
	                stockResponseDTO = new StockInvoiceResponseDTO();
	                stockResponseDTO.setStockId(stockId);
	                stockResponseDTO.setInvoiceNo((Integer) row[1]);
	                stockResponseDTO.setInvoiceDate((String) row[2]);
	                stockResponseDTO.setBillTotal((Double) row[3]);
	                stockResponseDTO.setDiscountPercent((Double) row[4]);
	                stockResponseDTO.setDiscountAmount((Double) row[5]);
	                stockResponseDTO.setTotalAfterDiscount((Double) row[6]);
	                stockResponseDTO.setGstPercent((Double) row[7]);
	                stockResponseDTO.setGstAmount((Double) row[8]);
	                stockResponseDTO.setGrandTotal((Double) row[9]);
	                stockResponseDTO.setLedgerName((String) row[10]);
	                stockResponseDTO.setStockDetails(new ArrayList<>());
	                stockMap.put(stockId, stockResponseDTO);
	            }

	            StockDetailDTOs stockDetailDTO = new StockDetailDTOs();
	            stockDetailDTO.setStockDetailId((Integer) row[11]);
	            stockDetailDTO.setBookQty((Integer) row[12]);
	            stockDetailDTO.setBookRate((Integer) row[13]);
	            stockDetailDTO.setBook_amount((Integer) row[14]);
	            stockDetailDTO.setBookName((String) row[15]);

	            stockResponseDTO.getStockDetails().add(stockDetailDTO);
	        }

	        List<StockInvoiceResponseDTO> stockResponseDTOList = new ArrayList<>(stockMap.values());
	        return new ApiResponseDTO<>(true, "Stock details retrieved successfully", stockResponseDTOList, HttpStatus.OK.value());
	    }

	    @SuppressWarnings("unchecked")
	    @Transactional
	    public List<Object[]> fetchStockDetails(String startDate, String endDate) {
	        String queryStr = "SELECT " +
	                "is2.stock_id, is2.invoiceNo, is2.invoiceDate, is2.billTotal, " +
	                "is2.discountPercent, is2.discountAmount, is2.totalAfterDiscount, " +
	                "is2.gstPercent, is2.gstAmount, is2.grandTotal, al.ledgerName, " +
	                "is3.stockDetailId, is3.book_qty, is3.book_rate, is3.book_amount, ib.bookName " +
	                "FROM invt_stock is2 " +
	                "JOIN acc_ledger al ON al.ledgerID = is2.ledgerIDF " +
	                "JOIN invt_stockdetail is3 ON is3.stock_idF = is2.stock_id " +
	                "JOIN invt_book ib ON ib.bookId = is3.book_idF " +
	                "WHERE is2.stock_type = 'A1' " +
	                "AND DATE(CONCAT(SUBSTRING(is2.invoiceDate, 7, 4), '-', SUBSTRING(is2.invoiceDate, 4, 2), '-', SUBSTRING(is2.invoiceDate, 1, 2))) " +
	                "BETWEEN :startDate AND :endDate " +
	                "ORDER BY DATE(CONCAT(SUBSTRING(is2.invoiceDate, 7, 4), '-', SUBSTRING(is2.invoiceDate, 4, 2), '-', SUBSTRING(is2.invoiceDate, 1, 2)))";

	        Query query = entityManager.createNativeQuery(queryStr);
	        query.setParameter("startDate", startDate);
	        query.setParameter("endDate", endDate);

	        return query.getResultList();
	    }

	
	
	
	
//	.------------------------------------------------------------------

	public ApiResponseDTO<Void> deleteStockById(int id) {
		Stock stock = stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));

		stockRepository.deleteById(id);

		return new ApiResponseDTO<>(true, "Stock deleted successfully", null, HttpStatus.OK.value());
	}

	private StockResponseDTO mapToStockResponseDTO(Stock stock) {
		StockResponseDTO stockResponseDTO = new StockResponseDTO();
		stockResponseDTO.setStockId(stock.getStockId());
		stockResponseDTO.setStockType(stock.getStock_type());
		stockResponseDTO.setInvoiceNo(stock.getInvoiceNo());
		stockResponseDTO.setInvoiceDate(stock.getInvoiceDate());
		stockResponseDTO.setBillTotal(stock.getBillTotal() != null ? stock.getBillTotal() : 0.0);
		stockResponseDTO.setDiscountPercent(stock.getDiscountPercent() != null ? stock.getDiscountPercent() : 0.0);
		stockResponseDTO.setDiscountAmount(stock.getDiscountAmount() != null ? stock.getDiscountAmount() : 0.0);
		stockResponseDTO
				.setTotalAfterDiscount(stock.getTotalAfterDiscount() != null ? stock.getTotalAfterDiscount() : 0.0);
		stockResponseDTO.setGstPercent(stock.getGstPercent() != null ? stock.getGstPercent() : 0.0);
		stockResponseDTO.setGstAmount(stock.getGstAmount() != null ? stock.getGstAmount() : 0.0);
		stockResponseDTO.setGrandTotal(stock.getGrandTotal() != null ? stock.getGrandTotal() : 0.0);
		stockResponseDTO.setLedgerIDF(stock.getLedgerIDF());

		List<StockDetailResponseDTO> stockDetailResponseDTOs = new ArrayList<>();
		for (StockDetail stockDetail : stock.getStockDetails()) {
			StockDetailResponseDTO stockDetailResponseDTO = mapToStockDetailResponseDTO(stockDetail);
			stockDetailResponseDTOs.add(stockDetailResponseDTO);
		}
		stockResponseDTO.setStockDetails(stockDetailResponseDTOs);

		return stockResponseDTO;
	}

	@Transactional
	public Stock getissueById(int stockId) {
		return stockRepository.findById(stockId).orElseThrow(() -> new RuntimeException("Stock not found"));
	}

	@Transactional
	public List<Stock> getAllIsuee() {
		return stockRepository.findAll();
	}

	@Transactional
	public ApiResponseDTO<Stock> updateStock(int stockId, Stock updatedStock) {
		Stock existingStock = stockRepository.findById(stockId)
				.orElseThrow(() -> new RuntimeException("Stock not found"));

		existingStock.setStock_type(updatedStock.getStock_type());
		existingStock.setInvoiceNo(updatedStock.getInvoiceNo());
		existingStock.setInvoiceDate(updatedStock.getInvoiceDate());

		Stock savedStock = stockRepository.save(existingStock);

		return new ApiResponseDTO<>(true, "Stock updated successfully", savedStock, HttpStatus.OK.value());
	}

	private StockDetailResponseDTO mapToStockDetailResponseDTO(StockDetail stockDetail) {
		StockDetailResponseDTO stockDetailResponseDTO = new StockDetailResponseDTO();
		stockDetailResponseDTO.setStockDetailId(stockDetail.getStockDetailId());
		stockDetailResponseDTO.setBookIdF(mapToBookResponseDTO(stockDetail.getBook_idF()));
		stockDetailResponseDTO.setSrno(stockDetail.getSrno());
		stockDetailResponseDTO.setBookQty(stockDetail.getBook_qty());
		stockDetailResponseDTO.setBookRate(stockDetail.getBook_rate());
		stockDetailResponseDTO.setBookAmount(stockDetail.getBook_amount());
		stockDetailResponseDTO.setStockType(stockDetail.getStock_type());
		return stockDetailResponseDTO;
	}

	private BookResponseDTO mapToBookResponseDTO(Book book) {
		BookResponseDTO bookResponseDTO = new BookResponseDTO();
		bookResponseDTO.setBookId(book.getBookId());
		bookResponseDTO.setBookName(book.getBookName());
		return bookResponseDTO;
	}

	// -------------------------------------------- Book Issue
	// Methods-----------------------------------------------

	@Transactional
	public ApiResponseDTO<Stock> bookIssue(BookIssueRequestDto bookIssueRequestDto) {
		Stock stock = new Stock();
		stock.setStock_type("A2");
		stock.setInvoiceNo(bookIssueRequestDto.getInvoiceNo());
		stock.setInvoiceDate(bookIssueRequestDto.getInvoiceDate());
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		stock.setInvoice_time(LocalDateTime.now().format(timeFormatter));
		GeneralMember generalMember = generalMemberRepository.findById(bookIssueRequestDto.getGeneralMemberId())
				.orElseThrow(() -> new RuntimeException("General member not found"));
		stock.setGeneralMember(generalMember);
		Stock savedStock = stockRepository.save(stock);
		for (BookDetailDto bookDetailDto : bookIssueRequestDto.getBookDetails()) {
			StockDetail stockDetail = new StockDetail();
			stockDetail.setStockIdF(savedStock);
			Book book = bookRepository.findById(bookDetailDto.getBookId())
					.orElseThrow(() -> new RuntimeException("Book not found"));
			stockDetail.setBook_idF(book);
			stockDetail.setStock_type("A2");
	        stockDetail.setBookDetailIdF(bookDetailDto.getBookdetailId());
			stockDetail.setBook_qty(1);
			stockDetailRepository.save(stockDetail);
			StockCopyNo stockCopyNo = new StockCopyNo();
			stockCopyNo.setStockType("A2");
			stockCopyNo.setStockDetailIdF(stockDetail);
			BookDetails bookDetails = bookDetailsRepository.findById(bookDetailDto.getBookdetailId())
					.orElseThrow(() -> new RuntimeException("Book details not found"));
			bookDetails.setBookIssue("N");
			stockCopyNo.setBookDetailIdF(bookDetails);

			bookDetailsRepository.save(bookDetails);
			stockCopyNoRepository.save(stockCopyNo);
		}
		return new ApiResponseDTO<>(true, "Book issued successfully", savedStock, HttpStatus.CREATED.value());
	}



	public ResponseEntity<ApiResponseDTO<List<BookIssue>>> getAllIssue(String startDate, String endDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate start = LocalDate.parse(startDate, inputFormatter);
        LocalDate end = LocalDate.parse(endDate, inputFormatter);

        int startYear = start.getYear();
        int endYear = end.getYear();

        try {
            if (!sessionService.checkCurrentYear(startYear) || !sessionService.checkCurrentYear(endYear)) {
                ApiResponseDTO<List<BookIssue>> response = new ApiResponseDTO<>(false, "No sessions found for the provided year range", new ArrayList<>(), HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            System.err.println("Session check failed: " + e.getMessage());
            ApiResponseDTO<List<BookIssue>> response = new ApiResponseDTO<>(false, "No sessions found for the current year", new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        List<BookIssue> issues = stockRepository.getAllIssue(startDate, endDate);
        ApiResponseDTO<List<BookIssue>> response = new ApiResponseDTO<>(true, "Data retrieved successfully", issues, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

	public List<IssueDetailsDTO> getInvoiceDetailsByStockId(Integer stockId) {
		List<Object[]> result = stockRepository.findIssueDetailsById(stockId);
		List<IssueDetailsDTO> issueDetailsList = new ArrayList<>();

		for (Object[] row : result) {
			IssueDetailsDTO issueDetails = new IssueDetailsDTO();
			issueDetails.setId((Integer) row[0]);
			issueDetails.setInvoiceNo(String.valueOf(row[1])); // Casting to String
			issueDetails.setInvoiceDate(String.valueOf(row[2])); // Casting to String
			issueDetails.setUser(String.valueOf(row[3])); // Casting to String
			issueDetails.setFirstName(String.valueOf(row[4])); // Casting to String
			issueDetails.setMiddleName(String.valueOf(row[5])); // Casting to String
			issueDetails.setLastName(String.valueOf(row[6])); // Casting to String

			String booksJson = String.valueOf(row[7]); // Casting to String
			List<BookDetailss> books = new ArrayList<>();
			try {
				JSONArray booksArray = new JSONArray(booksJson);
				for (int i = 0; i < booksArray.length(); i++) {
					JSONObject bookObject = booksArray.getJSONObject(i);
					BookDetailss bookDetailss = new BookDetailss();
					bookDetailss.setBookName(bookObject.getString("bookName"));
					bookDetailss.setAccessionNo(bookObject.getString("accessionNo"));
					bookDetailss.setBookDetailId(bookObject.getInt("bookDetailId"));
					books.add(bookDetailss);
				}
			} catch (JSONException e) {
				e.printStackTrace(); // Handle exception appropriately
			}
			issueDetails.setBooks(books);

			issueDetailsList.add(issueDetails);
		}
		return issueDetailsList;
	}

	@Transactional
	public ApiResponseDTO<Stock> updateBookIssue(int stockId, BookIssueRequestDto bookIssueRequestDto) {
		Stock stockToUpdate = stockRepository.findById(stockId)
				.orElseThrow(() -> new RuntimeException("Stock not found"));
		stockToUpdate.setInvoiceNo(bookIssueRequestDto.getInvoiceNo());
		stockToUpdate.setInvoiceDate(bookIssueRequestDto.getInvoiceDate());

		GeneralMember generalMember = generalMemberRepository.findById(bookIssueRequestDto.getGeneralMemberId())
				.orElseThrow(() -> new RuntimeException("General member not found"));
		stockToUpdate.setGeneralMember(generalMember);

		List<StockDetail> existingStockDetails = stockToUpdate.getStockDetails();
		List<BookDetailDto> newBookDetails = bookIssueRequestDto.getBookDetails();

		for (int i = 0; i < existingStockDetails.size(); i++) {
			StockDetail stockDetail = existingStockDetails.get(i);
			if (i < newBookDetails.size()) {
				BookDetailDto bookDetailDto = newBookDetails.get(i);
				Book book = bookRepository.findById(bookDetailDto.getBookId())
						.orElseThrow(() -> new RuntimeException("Book not found"));
				stockDetail.setBook_idF(book);
				stockDetailRepository.save(stockDetail);
			}
		}
		Stock updatedStock = stockRepository.save(stockToUpdate);

		return new ApiResponseDTO<>(true, "Book issue details updated successfully", updatedStock,
				HttpStatus.OK.value());
	}

	@Transactional
	public ApiResponseDTO<Void> deleteBookIssue(int stockId) {
		Stock stockToDelete = stockRepository.findById(stockId)
				.orElseThrow(() -> new RuntimeException("Stock not found"));

		List<StockDetail> stockDetails = stockToDelete.getStockDetails();
		for (StockDetail stockDetail : stockDetails) {
			List<StockCopyNo> stockCopyNos = stockDetail.getStockCopyNos();
			stockCopyNoRepository.deleteAll(stockCopyNos);
		}

		stockDetailRepository.deleteAll(stockDetails);

		stockRepository.delete(stockToDelete);

		return new ApiResponseDTO<>(true, "Book issue deleted successfully", null, HttpStatus.OK.value());
	}

	// ------------------------------------------------------- Issue Return
	// --------------------------------------------

	public List<GetAllIssueBookDetailsByUsername> getStockDetailsByUsernameAndReturnDate(int memberId,
			String returnDate) {
		return stockRepository.findStockDetailsByUsernameAndReturnDate(memberId, returnDate);
	}

	@Transactional
	public ApiResponseDTO<Void> createIssueReturn(BookIssueReturnRequestDTO bookIssueReturnRequestDTO) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		Stock stock = new Stock();
		stock.setStock_type("A3");
		stock.setInvoiceNo(bookIssueReturnRequestDTO.getIssueNo());
		stock.setInvoiceDate(bookIssueReturnRequestDTO.getIssueReturnDate());
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		stock.setInvoice_time(LocalDateTime.now().format(timeFormatter));

		GeneralMember generalMember = generalMemberRepository.findById(bookIssueReturnRequestDTO.getMemberId())
				.orElseThrow(() -> new RuntimeException("General member not found"));
		stock.setGeneralMember(generalMember);

		Stock savedStock = stockRepository.save(stock);

		for (BookDetailsDTO bookDetailsDTO : bookIssueReturnRequestDTO.getBookDetailsList()) {
			StockDetail stockDetail = new StockDetail();
			stockDetail.setStockIdF(savedStock);
			stockDetail.setStock_type("A3");
			stockDetail.setBookDetailIdF(bookDetailsDTO.getBookDetailIds());
			stockDetail.setBook_qty(1);
			stockDetail.setRef_issue_date(bookDetailsDTO.getIssuedate());
			stockDetail.setRef_issue_stockDetailId(bookDetailsDTO.getStockDetailId());
			stockDetail.setFineDays(bookDetailsDTO.getFineDays());
			stockDetail.setFinePerDays(bookDetailsDTO.getFinePerDays());
			stockDetail.setFineAmount(bookDetailsDTO.getFineAmount());

			Book book = bookRepository.findById(bookDetailsDTO.getBookId())
					.orElseThrow(() -> new RuntimeException("Book not found"));
			stockDetail.setBook_idF(book);

			stockDetailRepository.save(stockDetail);

			StockCopyNo stockCopyNo = new StockCopyNo();
			stockCopyNo.setStockDetailIdF(stockDetail);
			stockCopyNo.setStockType("A3");

			BookDetails bookDetails = bookDetailsRepository.findById(bookDetailsDTO.getBookDetailIds())
					.orElseThrow(() -> new RuntimeException("Book details not found"));

			bookDetails.setBookIssue("Y");
			bookDetailsRepository.save(bookDetails);
			stockCopyNo.setBookDetailIdF(bookDetails);
			stockCopyNoRepository.save(stockCopyNo);
		}

		return new ApiResponseDTO<>(true, "Issue return created successfully", null, HttpStatus.OK.value());
	}

	public ResponseEntity<ApiResponseDTO<List<Map<String, Object>>>> findAllIssueReturn(String startDate, String endDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate start = LocalDate.parse(startDate, inputFormatter);
        LocalDate end = LocalDate.parse(endDate, inputFormatter);

        int startYear = start.getYear();
        int endYear = end.getYear();

        try {
            if (!sessionService.checkCurrentYear(startYear) || !sessionService.checkCurrentYear(endYear)) {
                ApiResponseDTO<List<Map<String, Object>>> response = new ApiResponseDTO<>(false, "No sessions found for the provided year range", new ArrayList<>(), HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            System.err.println("Session check failed: " + e.getMessage());
            ApiResponseDTO<List<Map<String, Object>>> response = new ApiResponseDTO<>(false, "No sessions found for the current year", new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        List<Map<String, Object>> stockDetails = stockRepository.getStockDetailsWithBookDetails(startDate, endDate);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Map<String, Object> stockDetail : stockDetails) {
            try {
                String bookDetailsListStr = (String) stockDetail.get("bookDetailsList");
                List<Map<String, Object>> bookDetailsList = objectMapper.readValue(bookDetailsListStr, new TypeReference<List<Map<String, Object>>>() {});

                Map<String, Object> newStockDetail = new HashMap<>(stockDetail);
                newStockDetail.put("bookDetailsList", bookDetailsList);

                result.add(newStockDetail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ApiResponseDTO<List<Map<String, Object>>> response = new ApiResponseDTO<>(true, "Data retrieved successfully", result, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

	// ------------------------------------------ Purchase Return
	// ---------------------------------------------

	@Transactional
	public ApiResponseDTO<Void> createPurchaseReturn(PurchaseReturnRequestDTO purchaseReturnRequestDTO) {
		Ledger ledger = ledgerRepository.findById(purchaseReturnRequestDTO.getLedgerId())
				.orElseThrow(() -> new RuntimeException("Ledger not found"));

		Stock stock = new Stock();
		stock.setStock_type("A4");
		stock.setInvoiceNo(purchaseReturnRequestDTO.getInvoiceNO());
		stock.setInvoiceDate(purchaseReturnRequestDTO.getInvoiceDate());
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		stock.setInvoice_time(LocalDateTime.now().format(timeFormatter));
		stock.setBillTotal(purchaseReturnRequestDTO.getBillTotal());
		stock.setDiscountPercent(purchaseReturnRequestDTO.getDiscountPercent());
		stock.setDiscountAmount(purchaseReturnRequestDTO.getDiscountAmount());
		stock.setGstPercent(purchaseReturnRequestDTO.getGstPercent());
		stock.setGstAmount(purchaseReturnRequestDTO.getGstAmount());
		stock.setTotalAfterDiscount(purchaseReturnRequestDTO.getTotalAfterDiscount());
		stock.setGrandTotal(purchaseReturnRequestDTO.getGrandTotal());
		stock.setLedgerIDF(ledger);

		List<StockDetail> stockDetails = new ArrayList<>();
		List<StockCopyNo> stockCopyNos = new ArrayList<>();

		for (PurchaseReturnBookDetailDTO bookDetailDTO : purchaseReturnRequestDTO.getBookDetails()) {
			BookDetails bookDetails = bookDetailsRepository.findById(bookDetailDTO.getBookdetailId())
					.orElseThrow(() -> new RuntimeException("Book details not found"));
			StockDetail stockDetail = bookDetails.getStockDetailIdF();
			StockDetail returnStockDetail = new StockDetail();
			returnStockDetail.setStockIdF(stock);
			returnStockDetail.setBook_idF(stockDetail.getBook_idF());
			returnStockDetail.setBook_qty(1);
			returnStockDetail.setBook_rate(0);
			returnStockDetail.setStock_type("A4");
			returnStockDetail.setBookDetailIdF(bookDetailDTO.getBookdetailId());
			returnStockDetail.setBook_amount(bookDetailDTO.getAmount());
			bookDetails.setBook_return("Y");
			stockDetails.add(returnStockDetail);
			bookDetailsRepository.save(bookDetails);
			StockCopyNo stockCopyNo = new StockCopyNo();
			stockCopyNo.setStockDetailIdF(returnStockDetail);
			stockCopyNo.setBookDetailIdF(bookDetails);
			stockCopyNo.setStockType("A4");
			stockCopyNos.add(stockCopyNo);
		}
		stock.setStockDetails(stockDetails);
		stockRepository.save(stock);
		stockDetailRepository.saveAll(stockDetails);
		stockCopyNoRepository.saveAll(stockCopyNos);
		return new ApiResponseDTO<>(true, "Purchase return created successfully", null, HttpStatus.CREATED.value());
	}

	 public ResponseEntity<ApiResponseDTO<List<Map<String, Object>>>> getStockDetailsByType(String startDate, String endDate) {
	        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        LocalDate start = LocalDate.parse(startDate, inputFormatter);
	        LocalDate end = LocalDate.parse(endDate, inputFormatter);

	        int startYear = start.getYear();
	        int endYear = end.getYear();

	        try {
	            if (!sessionService.checkCurrentYear(startYear) || !sessionService.checkCurrentYear(endYear)) {
	                ApiResponseDTO<List<Map<String, Object>>> response = new ApiResponseDTO<>(false, "No sessions found for the provided year range", new ArrayList<>(), HttpStatus.BAD_REQUEST.value());
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	            }
	        } catch (Exception e) {
	            System.err.println("Session check failed: " + e.getMessage());
	            ApiResponseDTO<List<Map<String, Object>>> response = new ApiResponseDTO<>(false, "No sessions found for the current year", new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }

	        List<Map<String, Object>> results = stockRepository.findStockDetailsByType(startDate, endDate);
	        List<Map<String, Object>> formattedResults = new ArrayList<>();
	        ObjectMapper objectMapper = new ObjectMapper();

	        for (Map<String, Object> result : results) {
	            try {
	                Map<String, Object> newResult = new HashMap<>(result);
	                String booksJson = (String) result.get("books");
	                List<Map<String, Object>> books = objectMapper.readValue("[" + booksJson + "]", new TypeReference<List<Map<String, Object>>>() {});
	                newResult.put("books", books);
	                formattedResults.add(newResult);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

	        ApiResponseDTO<List<Map<String, Object>>> response = new ApiResponseDTO<>(true, "Data retrieved successfully", formattedResults, HttpStatus.OK.value());
	        return ResponseEntity.ok(response);
	    }
	// ------------------------------------------ Book Lost
	// ---------------------------------------------

	@Transactional
	public ApiResponseDTO<Void> createBookLost(PurchaseReturnRequestDTO purchaseReturnRequestDTO) {
		GeneralMember member = generalMemberRepository.findById(purchaseReturnRequestDTO.getMemberId())
				.orElseThrow(() -> new RuntimeException("Member not found"));

		Stock stock = new Stock();
		stock.setStock_type("A5");
		stock.setInvoiceNo(purchaseReturnRequestDTO.getInvoiceNO());
		stock.setInvoiceDate(purchaseReturnRequestDTO.getInvoiceDate());
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		stock.setInvoice_time(LocalDateTime.now().format(timeFormatter));
		stock.setBillTotal(purchaseReturnRequestDTO.getBillTotal());
		stock.setDiscountPercent(purchaseReturnRequestDTO.getDiscountPercent());
		stock.setDiscountAmount(purchaseReturnRequestDTO.getDiscountAmount());
		stock.setGstPercent(purchaseReturnRequestDTO.getGstPercent());
		stock.setGstAmount(purchaseReturnRequestDTO.getGstAmount());
		stock.setTotalAfterDiscount(purchaseReturnRequestDTO.getTotalAfterDiscount());
		stock.setGrandTotal(purchaseReturnRequestDTO.getGrandTotal());
		stock.setGeneralMember(member);

		List<StockDetail> stockDetails = new ArrayList<>();
		List<StockCopyNo> stockCopyNos = new ArrayList<>();

		for (PurchaseReturnBookDetailDTO bookDetailDTO : purchaseReturnRequestDTO.getBookDetails()) {
			BookDetails bookDetails = bookDetailsRepository.findById(bookDetailDTO.getBookdetailId())
					.orElseThrow(() -> new RuntimeException("Book details not found"));
			StockDetail stockDetail = bookDetails.getStockDetailIdF();
			StockDetail returnStockDetail = new StockDetail();
			returnStockDetail.setStockIdF(stock);
			returnStockDetail.setBook_idF(stockDetail.getBook_idF());
			returnStockDetail.setBook_qty(1);
			returnStockDetail.setBook_rate(0);
			returnStockDetail.setStock_type("A5");
			returnStockDetail.setBookDetailIdF(bookDetailDTO.getBookdetailId());
			returnStockDetail.setBook_amount(bookDetailDTO.getAmount());
			bookDetails.setBookLost("Y");
			stockDetails.add(returnStockDetail);
			bookDetailsRepository.save(bookDetails);
			StockCopyNo stockCopyNo = new StockCopyNo();
			stockCopyNo.setStockDetailIdF(returnStockDetail);
			stockCopyNo.setBookDetailIdF(bookDetails);
			stockCopyNo.setStockType("A5");
			stockCopyNos.add(stockCopyNo);
		}
		stock.setStockDetails(stockDetails);
		stockRepository.save(stock);
		stockDetailRepository.saveAll(stockDetails);
		stockCopyNoRepository.saveAll(stockCopyNos);
		return new ApiResponseDTO<>(true, "Book lost recorded successfully", null, HttpStatus.CREATED.value());
	}

	public ResponseEntity<ApiResponseDTO<List<Map<String, Object>>>> getLostDetails(String startDate, String endDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate start = LocalDate.parse(startDate, inputFormatter);
        LocalDate end = LocalDate.parse(endDate, inputFormatter);

        int startYear = start.getYear();
        int endYear = end.getYear();

        try {
            if (!sessionService.checkCurrentYear(startYear) || !sessionService.checkCurrentYear(endYear)) {
                ApiResponseDTO<List<Map<String, Object>>> response = new ApiResponseDTO<>(false, "No sessions found for the provided year range", null, HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            System.err.println("Session check failed: " + e.getMessage());
            ApiResponseDTO<List<Map<String, Object>>> response = new ApiResponseDTO<>(false, "No sessions found for the current year", null, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        List<Map<String, Object>> results = stockRepository.findBookLost(startDate, endDate);

        List<Map<String, Object>> formattedResults = results.stream().map(result -> {
            Map<String, Object> modifiableResult = new HashMap<>(result);
            try {
                String bookDetailsStr = (String) result.get("bookDetails");
                List<Map<String, Object>> bookDetails = objectMapper.readValue(bookDetailsStr, List.class);
                modifiableResult.put("bookDetails", bookDetails);
            } catch (JsonProcessingException e) {
                e.printStackTrace(); // Handle this appropriately in your application
            }
            return modifiableResult;
        }).collect(Collectors.toList());

        ApiResponseDTO<List<Map<String, Object>>> response = new ApiResponseDTO<>(true, "Data retrieved successfully", formattedResults, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
	// ------------------------------------------ Book scrap
	// --------------------------------------------

	@Transactional
	public ApiResponseDTO<Void> createBookScrap(BookLostRequestDTO bookLostRequestDTO) {
		Ledger ledger = ledgerRepository.findById(bookLostRequestDTO.getLedgerId())
				.orElseThrow(() -> new RuntimeException("Ledger not found"));

		Stock stock = new Stock();
		stock.setStock_type("A6");
		stock.setInvoiceNo(bookLostRequestDTO.getInvoiceNO());
		stock.setInvoiceDate(bookLostRequestDTO.getInvoiceDate());
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		stock.setInvoice_time(LocalDateTime.now().format(timeFormatter));
		stock.setBillTotal(bookLostRequestDTO.getBillTotal());
		stock.setDiscountPercent(bookLostRequestDTO.getDiscountPercent()); // Set discount percentage
		stock.setDiscountAmount(bookLostRequestDTO.getDiscountAmount()); // Set discount amount
		stock.setTotalAfterDiscount(bookLostRequestDTO.getTotalAfterDiscount()); // Set total after discount
		stock.setGrandTotal(bookLostRequestDTO.getGrandTotal()); // Set grand total
		stock.setLedgerIDF(ledger);

		List<StockDetail> stockDetails = new ArrayList<>();
		List<StockCopyNo> stockCopyNos = new ArrayList<>();

		for (BookLostBookDetailDTO bookDetailDTO : bookLostRequestDTO.getBookDetails()) {
			BookDetails bookDetails = bookDetailsRepository.findById(bookDetailDTO.getBookdetailId())
					.orElseThrow(() -> new RuntimeException("Book details not found"));
			StockDetail stockDetail = bookDetails.getStockDetailIdF();
			StockDetail returnStockDetail = new StockDetail();
			returnStockDetail.setStockIdF(stock);
			returnStockDetail.setBook_idF(stockDetail.getBook_idF());
			returnStockDetail.setStock_type("A6");
			returnStockDetail.setBook_qty(1); // Set bookQty
			returnStockDetail.setBookDetailIdF(bookDetailDTO.getBookdetailId());
			returnStockDetail.setBook_rate(bookDetailDTO.getAmount());
			bookDetails.setBookScrap("Y");
			stockDetails.add(returnStockDetail);
			bookDetailsRepository.save(bookDetails);
			StockCopyNo stockCopyNo = new StockCopyNo();
			stockCopyNo.setStockDetailIdF(returnStockDetail);
			stockCopyNo.setBookDetailIdF(bookDetails);
			stockCopyNo.setStockType("A6");
			stockCopyNos.add(stockCopyNo);
		}
		stock.setStockDetails(stockDetails);
		stockRepository.save(stock);
		stockDetailRepository.saveAll(stockDetails);
		stockCopyNoRepository.saveAll(stockCopyNos);
		return new ApiResponseDTO<>(true, "Book scrap recorded successfully", null, HttpStatus.CREATED.value());
	}

	public ResponseEntity<ApiResponseDTO<List<Map<String, Object>>>> getScrapDetails(String startDate, String endDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate start = LocalDate.parse(startDate, inputFormatter);
        LocalDate end = LocalDate.parse(endDate, inputFormatter);

        int startYear = start.getYear();
        int endYear = end.getYear();

        try {
            if (!sessionService.checkCurrentYear(startYear) || !sessionService.checkCurrentYear(endYear)) {
                ApiResponseDTO<List<Map<String, Object>>> response = new ApiResponseDTO<>(false, "No sessions found for the provided year range", null, HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            System.err.println("Session check failed: " + e.getMessage());
            ApiResponseDTO<List<Map<String, Object>>> response = new ApiResponseDTO<>(false, "No sessions found for the current year", null, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        List<Map<String, Object>> results = stockRepository.findBookScrap(startDate, endDate);

        List<Map<String, Object>> formattedResults = results.stream().map(result -> {
            Map<String, Object> modifiableResult = new HashMap<>(result);
            try {
                String bookDetailsStr = (String) result.get("bookDetails");
                // Check if bookDetailsStr is a single JSON object
                if (bookDetailsStr.startsWith("{") && bookDetailsStr.endsWith("}")) {
                    // Parse as a single object
                    Map<String, Object> bookDetail = objectMapper.readValue(bookDetailsStr, new HashMap<String, Object>().getClass());
                    modifiableResult.put("bookDetails", List.of(bookDetail)); // Convert to list for consistency
                } else {
                    // Parse as JSON array if necessary (adjust logic if bookDetails can be an array)
                    List<Map<String, Object>> bookDetails = objectMapper.readValue(bookDetailsStr, List.class);
                    modifiableResult.put("bookDetails", bookDetails);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace(); // Handle this appropriately in your application
            }
            return modifiableResult;
        }).collect(Collectors.toList());

        ApiResponseDTO<List<Map<String, Object>>> response = new ApiResponseDTO<>(true, "Data retrieved successfully", formattedResults, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
	public List<StockModel> getStockDetials() {
		return stockRepository.getAllStock();
	}
	
	public List<AcessionForLostScarap> AcessionForLostScarap() {
		return stockRepository.GetAccesionNoForTransactions();
	}
}


