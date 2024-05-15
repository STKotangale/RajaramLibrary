package com.raja.lib.invt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import com.raja.lib.acc.model.Ledger;
import com.raja.lib.acc.repository.LedgerRepository;
import com.raja.lib.invt.model.Book;
import com.raja.lib.invt.model.BookDetails;
import com.raja.lib.invt.model.Stock;
import com.raja.lib.invt.model.StockDetail;
import com.raja.lib.invt.repository.BookDetailsRepository;
import com.raja.lib.invt.repository.BookRepository;
import com.raja.lib.invt.repository.StockDetailRepository;
import com.raja.lib.invt.repository.StockRepository;
import com.raja.lib.invt.request.StockDetailRequestDTO;
import com.raja.lib.invt.request.StockRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.resposne.BookResponseDTO;
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
//
//	@Transactional
//	@Caching(evict = { @CacheEvict(value = "stocks", allEntries = true),
//			@CacheEvict(value = "stockById", key = "#result.data.stockId") })
	public ApiResponseDTO<Stock> createStock(StockRequestDTO stockRequestDTO) throws NotFoundException {
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

		return new ApiResponseDTO<>(true, "Stock created successfully", savedStock, HttpStatus.CREATED.value());
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

//	@Transactional(readOnly = true)
//	@Cacheable(value = "stockById", key = "#stockId", unless = "#result == null || #result.data == null")
	public ApiResponseDTO<StockResponseDTO> getStockById(int stockId) {
		Stock stock = stockRepository.findById(stockId)
				.orElseThrow(() -> new RuntimeException("Stock not found with id: " + stockId));

		StockResponseDTO stockResponseDTO = mapToStockResponseDTO(stock);

		return new ApiResponseDTO<>(true, "Stock found", stockResponseDTO, HttpStatus.OK.value());
	}

//	@Transactional(readOnly = true)
//	@Cacheable(value = "stocks", unless = "#result == null || #result.data == null || #result.data.isEmpty()")
	public ApiResponseDTO<List<StockResponseDTO>> getAllStocks() {
		List<Stock> stocks = stockRepository.findAll();

		List<StockResponseDTO> stockResponseDTOs = new ArrayList<>();
		for (Stock stock : stocks) {
			StockResponseDTO stockResponseDTO = mapToStockResponseDTO(stock);
			stockResponseDTOs.add(stockResponseDTO);
		}

		return new ApiResponseDTO<>(true, "All stocks found", stockResponseDTOs, HttpStatus.OK.value());
	}

//	@Transactional
//	@Caching(evict = { @CacheEvict(value = "stocks", allEntries = true),
//			@CacheEvict(value = "stockById", key = "#id") })
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
//
//	@Transactional
//	@Caching(evict = { @CacheEvict(value = "stocks", allEntries = true),
//	        @CacheEvict(value = "stockById", key = "#result.data.stockId") })
	public ApiResponseDTO<Stock> bookIssue(StockRequestDTO stockRequestDTO) throws NotFoundException {
	    Stock stock = new Stock();
	    stock.setStock_type("A2"); 
	    stock.setInvoiceNo(stockRequestDTO.getInvoiceNo());
	    stock.setInvoiceDate(stockRequestDTO.getInvoiceDate());

	    Stock savedStock = stockRepository.save(stock);

	    List<StockDetail> stockDetails = new ArrayList<>();
	    for (StockDetailRequestDTO detailDTO : stockRequestDTO.getStockDetails()) {
	        StockDetail stockDetail = new StockDetail();
	        stockDetail.setStockIdF(savedStock); 
	        Book book = bookRepository.findById(detailDTO.getBookIdF())
	                .orElseThrow(() -> new NotFoundException());
	        stockDetail.setBook_idF(book); 
	        stockDetails.add(stockDetail);
	    }
	    stockDetailRepository.saveAll(stockDetails);

	    return new ApiResponseDTO<>(true, "Book issued successfully", savedStock, HttpStatus.CREATED.value());
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

}
