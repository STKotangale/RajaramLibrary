package com.raja.lib.invt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raja.lib.acc.model.Ledger;
import com.raja.lib.acc.repository.LedgerRepository;
import com.raja.lib.invt.model.Book;
import com.raja.lib.invt.model.Stock;
import com.raja.lib.invt.model.StockDetail;
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

    @Transactional
    public ApiResponseDTO<Stock> createStock(StockRequestDTO stockRequestDTO) throws NotFoundException {
        Ledger ledger = ledgerRepository.findById(stockRequestDTO.getLedgerIDF())
                .orElseThrow(() -> new NotFoundException());

        Stock stock = new Stock();
        stock.setStock_type(stockRequestDTO.getStockType());
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

        for (StockDetailRequestDTO detailDTO : stockRequestDTO.getStockDetails()) {
            Book book = bookRepository.findById(detailDTO.getBookIdF())
                    .orElseThrow(() -> new NotFoundException());

            StockDetail stockDetail = new StockDetail();
            stockDetail.setStockIdF(stock);
            stockDetail.setSrno(nextSrno++);
            stockDetail.setBook_qty(detailDTO.getBookQty());
            stockDetail.setBook_rate(detailDTO.getBookRate());
            stockDetail.setStock_type(detailDTO.getStockType());
            stockDetail.setBook_amount(detailDTO.getBookAmount());
            stockDetail.setBook_idF(book);
            stockDetails.add(stockDetail);
        }

        stock.setStockDetails(stockDetails);

        Stock savedStock = stockRepository.save(stock);
        return new ApiResponseDTO<>(true, "Stock created successfully", savedStock, HttpStatus.CREATED.value());
    }
    
    @Transactional(readOnly = true)
    public ApiResponseDTO<StockResponseDTO> getStockById(int stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + stockId));

        StockResponseDTO stockResponseDTO = mapToStockResponseDTO(stock);

        return new ApiResponseDTO<>(true, "Stock found", stockResponseDTO, HttpStatus.OK.value());
    }

    @Transactional(readOnly = true)
    public ApiResponseDTO<List<StockResponseDTO>> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();

        List<StockResponseDTO> stockResponseDTOs = new ArrayList<>();
        for (Stock stock : stocks) {
            StockResponseDTO stockResponseDTO = mapToStockResponseDTO(stock);
            stockResponseDTOs.add(stockResponseDTO);
        }

        return new ApiResponseDTO<>(true, "All stocks found", stockResponseDTOs, HttpStatus.OK.value());
    }

    @Transactional
    public ApiResponseDTO<Void> deleteStockById(int id) throws NotFoundException {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());

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
    public ApiResponseDTO<StockResponseDTO> updateStock(int stockId, StockRequestDTO stockRequestDTO) throws NotFoundException {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new NotFoundException());
        
        // Update stock attributes
        stock.setStock_type(stockRequestDTO.getStockType());
        stock.setInvoiceNo(stockRequestDTO.getInvoiceNo());
        stock.setInvoiceDate(stockRequestDTO.getInvoiceDate());
        stock.setBillTotal(stockRequestDTO.getBillTotal());
        stock.setDiscountPercent(stockRequestDTO.getDiscountPercent());
        stock.setDiscountAmount(stockRequestDTO.getDiscountAmount());
        stock.setTotalAfterDiscount(stockRequestDTO.getTotalAfterDiscount());
        stock.setGstPercent(stockRequestDTO.getGstPercent());
        stock.setGstAmount(stockRequestDTO.getGstAmount());
        stock.setGrandTotal(stockRequestDTO.getGrandTotal());
        // Update Ledger
        Ledger ledger = ledgerRepository.findById(stockRequestDTO.getLedgerIDF())
                .orElseThrow(() -> new NotFoundException());
        stock.setLedgerIDF(ledger);
        
        List<StockDetailRequestDTO> updatedStockDetailDTOs = stockRequestDTO.getStockDetails();
        List<StockDetail> stockDetails = stock.getStockDetails();

        // Iterate over the updated stock details
        for (StockDetailRequestDTO updatedDetailDTO : updatedStockDetailDTOs) {
            // Find the corresponding stock detail in the existing list by comparing attributes
            Optional<StockDetail> existingStockDetailOptional = stockDetails.stream()
                    .filter(detail -> detailMatchesRequest(detail, updatedDetailDTO))
                    .findFirst();

            if (existingStockDetailOptional.isPresent()) {
                StockDetail existingStockDetail = existingStockDetailOptional.get();
                // Update existing stock detail attributes
                existingStockDetail.setBook_qty(updatedDetailDTO.getBookQty());
                existingStockDetail.setBook_rate(updatedDetailDTO.getBookRate());
                existingStockDetail.setStock_type(updatedDetailDTO.getStockType());
                existingStockDetail.setBook_amount(updatedDetailDTO.getBookAmount());

                // Update associated Book if needed
                if (existingStockDetail.getBook_idF().getBookId() != updatedDetailDTO.getBookIdF()) {
                    Book updatedBook = bookRepository.findById(updatedDetailDTO.getBookIdF())
                            .orElseThrow(() -> new NotFoundException());
                    existingStockDetail.setBook_idF(updatedBook);
                }
            } else {
                // If the stock detail does not exist, it's a new one, so we add it
                StockDetail newStockDetail = new StockDetail();
                newStockDetail.setStockIdF(stock);
                newStockDetail.setBook_qty(updatedDetailDTO.getBookQty());
                newStockDetail.setBook_rate(updatedDetailDTO.getBookRate());
                newStockDetail.setStock_type(updatedDetailDTO.getStockType());
                newStockDetail.setBook_amount(updatedDetailDTO.getBookAmount());

                // Set associated Book
                Book associatedBook = bookRepository.findById(updatedDetailDTO.getBookIdF())
                        .orElseThrow(() -> new NotFoundException());
                newStockDetail.setBook_idF(associatedBook);

                // Add the new stock detail to the list of stock details
                stock.getStockDetails().add(newStockDetail);
            }
        }

        // Remove any existing stock details not present in the updated details
        stockDetails.removeIf(detail -> !updatedStockDetailDTOs.stream()
                .anyMatch(dto -> detailMatchesRequest(detail, dto)));

        // Save the updated stock
        Stock savedStock = stockRepository.save(stock);
        StockResponseDTO updatedStockResponseDTO = mapToStockResponseDTO(savedStock);

        return new ApiResponseDTO<>(true, "Stock details updated successfully", updatedStockResponseDTO, HttpStatus.OK.value());
    }

    private boolean detailMatchesRequest(StockDetail detail, StockDetailRequestDTO requestDTO) {
        return detail.getBook_idF().getBookId() == requestDTO.getBookIdF()
                && detail.getBook_qty() == requestDTO.getBookQty()
                && detail.getBook_rate() == requestDTO.getBookRate()
                && detail.getStock_type().equals(requestDTO.getStockType())
                && detail.getBook_amount() == requestDTO.getBookAmount();
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
