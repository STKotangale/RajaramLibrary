package com.raja.lib.invt.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raja.lib.acc.model.Ledger;
import com.raja.lib.acc.repository.LedgerRepository;
import com.raja.lib.auth.model.GeneralMember;
import com.raja.lib.auth.repository.GeneralMemberRepository;
import com.raja.lib.invt.model.Book;
import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.model.Stock;
import com.raja.lib.invt.model.StockCopyNo;
import com.raja.lib.invt.model.StockDetail;
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
import com.raja.lib.invt.resposne.StockDetailResponseDTO;
import com.raja.lib.invt.resposne.StockResponseDTO;

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

		// Find the largest purchaseCopyNo for the given book
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

	public ApiResponseDTO<List<StockResponseDTO>> getAllStocks() {
		List<Stock> stocks = stockRepository.findAll();

		List<StockResponseDTO> stockResponseDTOs = new ArrayList<>();
		for (Stock stock : stocks) {
			StockResponseDTO stockResponseDTO = mapToStockResponseDTO(stock);
			stockResponseDTOs.add(stockResponseDTO);
		}

		return new ApiResponseDTO<>(true, "All stocks found", stockResponseDTOs, HttpStatus.OK.value());
	}

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

//	public String getStockDetailsAsJson() {
//		List<String> jsonResults = stockRepository.getStockDetailsAsJson();
//		StringBuilder jsonResponse = new StringBuilder("[");
//		for (String jsonResult : jsonResults) {
//			jsonResponse.append(jsonResult).append(",");
//		}
//		if (jsonResponse.length() > 1) {
//			jsonResponse.deleteCharAt(jsonResponse.length() - 1);
//		}
//		jsonResponse.append("]");
//		return jsonResponse.toString();
//	}

	public List<BookIssue> getAllIssue() {
		return stockRepository.getAllIssue();
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

	public List<Map<String, Object>> findAllIssueReturn() {
		List<Map<String, Object>> stockDetails = stockRepository.getStockDetailsWithBookDetails();
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> result = new ArrayList<>();

		for (Map<String, Object> stockDetail : stockDetails) {
			try {
				String bookDetailsListStr = (String) stockDetail.get("bookDetailsList");
				JsonNode bookDetailsListJson = objectMapper.readTree(bookDetailsListStr);

				Map<String, Object> newStockDetail = new HashMap<>(stockDetail);
				newStockDetail.put("bookDetailsList", bookDetailsListJson);

				result.add(newStockDetail);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
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

	public List<PurchaseReturnDTO> getStockDetailsByType() {
		return stockRepository.findStockDetailsByType();
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

	public List<PurchaseReturnDTO> getLostDetials() {
		return stockRepository.findBookLost();
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

	public List<PurchaseReturnDTO> getScrapDetials() {
		return stockRepository.findBookScrap();
	}

	public List<StockModel> getStockDetials() {
		return stockRepository.getAllStock();
	}
}
