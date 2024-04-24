package com.raja.lib.invt.resposne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseDetailResponseDto {
    private Long purchaseDetailId;
    private Integer bookId;  
    private String bookName;
    private int qty;
    private int rate;
    private int amount;

    public PurchaseDetailResponseDto(Long purchaseDetailId, Integer bookId, String bookName, int qty, int rate, int amount) {
        this.purchaseDetailId = purchaseDetailId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.qty = qty;
        this.rate = rate;
        this.amount = amount;
    }
}
