package com.raja.lib.invt.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.raja.lib.acc.model.Ledger;
import com.raja.lib.acc.repository.LedgerRepository;
import com.raja.lib.auth.model.GeneralMember;
import com.raja.lib.auth.repository.GeneralMemberRepository;
import com.raja.lib.invt.model.Book;
import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.model.Stock;
import com.raja.lib.invt.model.StockCopyNo;
import com.raja.lib.invt.model.StockDetail;
import com.raja.lib.invt.objects.GetIssueDetilsByUser;
import com.raja.lib.invt.repository.BookDetailsRepository;
import com.raja.lib.invt.repository.BookRepository;
import com.raja.lib.invt.repository.StockCopyNoRepository;
import com.raja.lib.invt.repository.StockDetailRepository;
import com.raja.lib.invt.repository.StockRepository;
import com.raja.lib.invt.request.BookDetailDto;
import com.raja.lib.invt.request.BookDetailsDTO;
import com.raja.lib.invt.request.BookIssueRequestDto;
import com.raja.lib.invt.request.BookIssueReturnRequestDTO;
import com.raja.lib.invt.request.PurchaseReturnRequestDTO;
import com.raja.lib.invt.request.StockDetailRequestDTO;
import com.raja.lib.invt.request.StockRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.resposne.BookResponseDTO;
import com.raja.lib.invt.resposne.PurchaseReturnBookDetailDTO;
import com.raja.lib.invt.resposne.PurchaseReturnDTO;
import com.raja.lib.invt.resposne.StockDetailResponseDTO;
import com.raja.lib.invt.resposne.StockResponseDTO;

import jakarta.transaction.Transactional;
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

	@Transactional
	public ApiResponseDTO<Void> createStock(StockRequestDTO stockRequestDTO) throws NotFoundException {
		Ledger ledger = ledgerRepository.findById(stockRequestDTO.getLedgerIDF())
				.orElseThrow(() -> new NotFoundException());

		Stock stock = new Stock();
		stock.setStock_type("A1");
		stock.setInvoiceNo(stockRequestDTO.getInvoiceNo());
		stock.setInvoiceDate(stockRequestDTO.getInvoiceDate());
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
			Book book = bookRepository.findById(detailDTO.getBookIdF()).orElseThrow(() -> new NotFoundException());

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
		int purchaseCopyNo = 1;

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

	public ApiResponseDTO<Void> deleteStockById(int id) throws NotFoundException {
		Stock stock = stockRepository.findById(id).orElseThrow(() -> new NotFoundException());

		stockRepository.deleteById(id);

		return new ApiResponseDTO<>(true, "Stock deleted successfully", null, HttpStatus.OK.value());
	}

	private StockResponseDTO mapToStockResponseDTO(Stock stock) {
		StockResponseDTO stockResponseDTO = new StockResponseDTO();
		stockResponseDTO.setStockId(stock.getStockId());
		stockResponseDTO.setStockType(stock.getStock_type());
		stockResponseDTO.setInvoiceNo(stock.getInvoiceNo());
		stockResponseDTO.setInvoiceDate(stock.getInvoiceDate());
		stockResponseDTO.setBillTotal(stock.getBillTotal());
		stockResponseDTO.setDiscountPercent(stock.getDiscountPercent());
		stockResponseDTO.setDiscountAmount(stock.getDiscountAmount());
		stockResponseDTO.setTotalAfterDiscount(stock.getTotalAfterDiscount());
		stockResponseDTO.setGstPercent(stock.getGstPercent());
		stockResponseDTO.setGstAmount(stock.getGstAmount());
		stockResponseDTO.setGrandTotal(stock.getGrandTotal());
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
	public Stock getissueById(int stockId) throws NotFoundException {
		return stockRepository.findById(stockId).orElseThrow(() -> new NotFoundException());
	}

	@Transactional
	public List<Stock> getAllIsuee() {
		return stockRepository.findAll();
	}

	@Transactional
	public ApiResponseDTO<Stock> updateStock(int stockId, Stock updatedStock) throws NotFoundException {
		Stock existingStock = stockRepository.findById(stockId).orElseThrow(() -> new NotFoundException());

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

//	-------------------------------------------- Book Issue Methods-----------------------------------------------

	@Transactional
	public ApiResponseDTO<Stock> bookIssue(BookIssueRequestDto bookIssueRequestDto) throws NotFoundException {
		Stock stock = new Stock();
		stock.setStock_type("A2");
		stock.setInvoiceNo(bookIssueRequestDto.getInvoiceNo());
		stock.setInvoiceDate(bookIssueRequestDto.getInvoiceDate());
		GeneralMember generalMember = generalMemberRepository.findById(bookIssueRequestDto.getGeneralMemberId())
				.orElseThrow(() -> new NotFoundException());
		stock.setGeneralMember(generalMember);
		Stock savedStock = stockRepository.save(stock);
		for (BookDetailDto bookDetailDto : bookIssueRequestDto.getBookDetails()) {
			StockDetail stockDetail = new StockDetail();
			stockDetail.setStockIdF(savedStock);
			Book book = bookRepository.findById(bookDetailDto.getBookId()).orElseThrow(() -> new NotFoundException());
			stockDetail.setBook_idF(book);
			stockDetail.setStock_type("A2");
			stockDetailRepository.save(stockDetail);
			StockCopyNo stockCopyNo = new StockCopyNo();
			stockCopyNo.setStockType("A2");
			stockCopyNo.setStockDetailIdF(stockDetail);
			BookDetails bookDetails = bookDetailsRepository.findById(bookDetailDto.getBookdetailId())
					.orElseThrow(() -> new NotFoundException());
			bookDetails.setBookIssue("N");
			stockCopyNo.setBookDetailIdF(bookDetails);

			bookDetailsRepository.save(bookDetails);
			stockCopyNoRepository.save(stockCopyNo);
		}
		return new ApiResponseDTO<>(true, "Book issued successfully", savedStock, HttpStatus.CREATED.value());
	}

	public String getStockDetailsAsJson() {
		List<String> jsonResults = stockRepository.getStockDetailsAsJson();
		StringBuilder jsonResponse = new StringBuilder("[");
		for (String jsonResult : jsonResults) {
			jsonResponse.append(jsonResult).append(",");
		}
		if (jsonResponse.length() > 1) {
			jsonResponse.deleteCharAt(jsonResponse.length() - 1);
		}
		jsonResponse.append("]");
		return jsonResponse.toString();
	}

	@Transactional
	public ApiResponseDTO<Stock> updateBookIssue(int stockId, BookIssueRequestDto bookIssueRequestDto)
			throws NotFoundException {
		Stock stockToUpdate = stockRepository.findById(stockId).orElseThrow(() -> new NotFoundException());
		stockToUpdate.setInvoiceNo(bookIssueRequestDto.getInvoiceNo());
		stockToUpdate.setInvoiceDate(bookIssueRequestDto.getInvoiceDate());

		GeneralMember generalMember = generalMemberRepository.findById(bookIssueRequestDto.getGeneralMemberId())
				.orElseThrow(() -> new NotFoundException());
		stockToUpdate.setGeneralMember(generalMember);

		List<StockDetail> existingStockDetails = stockToUpdate.getStockDetails();
		List<BookDetailDto> newBookDetails = bookIssueRequestDto.getBookDetails();

		for (int i = 0; i < existingStockDetails.size(); i++) {
			StockDetail stockDetail = existingStockDetails.get(i);
			if (i < newBookDetails.size()) {
				BookDetailDto bookDetailDto = newBookDetails.get(i);
				Book book = bookRepository.findById(bookDetailDto.getBookId())
						.orElseThrow(() -> new NotFoundException());
				stockDetail.setBook_idF(book);
				stockDetailRepository.save(stockDetail);
			}
		}
		Stock updatedStock = stockRepository.save(stockToUpdate);

		return new ApiResponseDTO<>(true, "Book issue details updated successfully", updatedStock,
				HttpStatus.OK.value());
	}

	@Transactional
	public ApiResponseDTO<Void> deleteBookIssue(int stockId) throws NotFoundException {
		Stock stockToDelete = stockRepository.findById(stockId).orElseThrow(() -> new NotFoundException());

		List<StockDetail> stockDetails = stockToDelete.getStockDetails();
		for (StockDetail stockDetail : stockDetails) {
			List<StockCopyNo> stockCopyNos = stockDetail.getStockCopyNos();
			stockCopyNoRepository.deleteAll(stockCopyNos);
		}

		stockDetailRepository.deleteAll(stockDetails);

		stockRepository.delete(stockToDelete);

		return new ApiResponseDTO<>(true, "Book issue deleted successfully", null, HttpStatus.OK.value());
	}

//	------------------------------------------------------- Issue Return --------------------------------------------

	public List<GetIssueDetilsByUser> getStockDetailsByUsername(String username) {
		return stockRepository.findStockDetailsByUsername(username);
	}

	@Transactional
	public ApiResponseDTO<Void> createIssueReturn(BookIssueReturnRequestDTO bookIssueReturnRequestDTO)
			throws NotFoundException {
		Stock stock = new Stock();
		stock.setStock_type("A3");
		stock.setInvoiceNo(bookIssueReturnRequestDTO.getIssueNo());
		stock.setInvoiceDate(bookIssueReturnRequestDTO.getIssueReturnDate());

		GeneralMember generalMember = generalMemberRepository.findById(bookIssueReturnRequestDTO.getMemberId())
				.orElseThrow(() -> new NotFoundException());
		stock.setGeneralMember(generalMember);

		Stock savedStock = stockRepository.save(stock);

		for (BookDetailsDTO bookDetailsDTO : bookIssueReturnRequestDTO.getBookDetailsList()) {
			StockDetail stockDetail = new StockDetail();
			stockDetail.setStockIdF(savedStock);
			stockDetail.setStock_type("A3");

			Book book = bookRepository.findById(bookDetailsDTO.getBookId()).orElseThrow(() -> new NotFoundException());
			stockDetail.setBook_idF(book);

			stockDetailRepository.save(stockDetail);

			StockCopyNo stockCopyNo = new StockCopyNo();
			stockCopyNo.setStockDetailIdF(stockDetail);
			stockCopyNo.setStockType("A3");

			BookDetails bookDetails = bookDetailsRepository.findById(bookDetailsDTO.getBookDetailIds())
					.orElseThrow(() -> new NotFoundException());

			bookDetails.setBookIssue("Y");
			bookDetailsRepository.save(bookDetails);
			stockCopyNo.setBookDetailIdF(bookDetails);
			stockCopyNoRepository.save(stockCopyNo);
		}

		return new ApiResponseDTO<>(true, "Issue return created successfully", null, HttpStatus.CREATED.value());
	}

	public List<GetIssueDetilsByUser> findAllIssueReturn() {
		return stockRepository.findAllIssueReturn();
	}

//	------------------------------------------ Purchase Return ---------------------------------------------

	@Transactional
	public ApiResponseDTO<Void> createPurchaseReturn(PurchaseReturnRequestDTO purchaseReturnRequestDTO)
			throws NotFoundException {
		Ledger ledger = ledgerRepository.findById(purchaseReturnRequestDTO.getLedgerId())
				.orElseThrow(() -> new NotFoundException());

		Stock stock = new Stock();
		stock.setStock_type("A4");
		stock.setInvoiceNo(purchaseReturnRequestDTO.getInvoiceNO());
		stock.setInvoiceDate(purchaseReturnRequestDTO.getInvoiceDate());
		stock.setBillTotal(purchaseReturnRequestDTO.getBillTotal());
		stock.setDiscountPercent(purchaseReturnRequestDTO.getDiscount());
		stock.setDiscountAmount(purchaseReturnRequestDTO.getDiscount());
		stock.setTotalAfterDiscount(purchaseReturnRequestDTO.getTotalAfterDiscount());
		stock.setGrandTotal(purchaseReturnRequestDTO.getGrandTotal());
		stock.setLedgerIDF(ledger);

		List<StockDetail> stockDetails = new ArrayList<>();
		List<StockCopyNo> stockCopyNos = new ArrayList<>();

		for (PurchaseReturnBookDetailDTO bookDetailDTO : purchaseReturnRequestDTO.getBookDetails()) {
			BookDetails bookDetails = bookDetailsRepository.findById(bookDetailDTO.getBookdetailId())
					.orElseThrow(() -> new NotFoundException());
			StockDetail stockDetail = bookDetails.getStockDetailIdF();
			StockDetail returnStockDetail = new StockDetail();
			returnStockDetail.setStockIdF(stock);
			returnStockDetail.setBook_idF(stockDetail.getBook_idF());
			returnStockDetail.setBook_qty(0);
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
	
	
//	------------------------------------------ Book Scarp ---------------------------------------------
	@Transactional
	public ApiResponseDTO<Void> createBookLost(PurchaseReturnRequestDTO purchaseReturnRequestDTO)
			throws NotFoundException {
		Ledger ledger = ledgerRepository.findById(purchaseReturnRequestDTO.getLedgerId())
				.orElseThrow(() -> new NotFoundException());

		Stock stock = new Stock();
		stock.setStock_type("A5");
		stock.setInvoiceNo(purchaseReturnRequestDTO.getInvoiceNO());
		stock.setInvoiceDate(purchaseReturnRequestDTO.getInvoiceDate());
		stock.setBillTotal(purchaseReturnRequestDTO.getBillTotal());
		stock.setDiscountPercent(purchaseReturnRequestDTO.getDiscount());
		stock.setDiscountAmount(purchaseReturnRequestDTO.getDiscount());
		stock.setTotalAfterDiscount(purchaseReturnRequestDTO.getTotalAfterDiscount());
		stock.setGrandTotal(purchaseReturnRequestDTO.getGrandTotal());
		stock.setLedgerIDF(ledger);

		List<StockDetail> stockDetails = new ArrayList<>();
		List<StockCopyNo> stockCopyNos = new ArrayList<>();

		for (PurchaseReturnBookDetailDTO bookDetailDTO : purchaseReturnRequestDTO.getBookDetails()) {
			BookDetails bookDetails = bookDetailsRepository.findById(bookDetailDTO.getBookdetailId())
					.orElseThrow(() -> new NotFoundException());
			StockDetail stockDetail = bookDetails.getStockDetailIdF();
			StockDetail returnStockDetail = new StockDetail();
			returnStockDetail.setStockIdF(stock);
			returnStockDetail.setBook_idF(stockDetail.getBook_idF());
			returnStockDetail.setBook_qty(0);
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
		return new ApiResponseDTO<>(true, "Purchase return created successfully", null, HttpStatus.CREATED.value());
	}
	

	public List<PurchaseReturnDTO> getLostDetials() {
		return stockRepository.findbookLost();
	}

}
