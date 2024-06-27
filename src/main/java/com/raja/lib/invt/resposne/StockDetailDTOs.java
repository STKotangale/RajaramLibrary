package com.raja.lib.invt.resposne;
import lombok.Data;

@Data
public class StockDetailDTOs {
    private String bookName; 
    private int bookQty;
    private int bookRate;
    private int book_amount;
    private int stockDetailId;
}
