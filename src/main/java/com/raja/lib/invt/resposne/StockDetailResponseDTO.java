package com.raja.lib.invt.resposne;

import java.io.Serializable;

import lombok.Data;

@Data
public class StockDetailResponseDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int stockDetailId;
    private StockResponseDTO stockIdF;
    private int srno;
    private BookResponseDTO bookIdF;
    private String bookName; // New field for bookName
    private int bookQty;
    private int bookRate;
    private int bookAmount;
    private String stockType;
}
