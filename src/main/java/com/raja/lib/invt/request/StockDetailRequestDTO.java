package com.raja.lib.invt.request;


import lombok.Data;

@Data
public class StockDetailRequestDTO {
    private StockRequestDTO stockIdF;
    private int srno;
    private int bookIdF;
    private int bookQty;
    private int bookRate;
    private int bookAmount;
    private String stockType;
}
