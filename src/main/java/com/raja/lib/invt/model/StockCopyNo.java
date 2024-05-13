package com.raja.lib.invt.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "invt_stock_copy_no")
public class StockCopyNo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stockCopyId")
    private int stockCopyId ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockDetailIdF", nullable = false)
    private StockDetail stockDetailIdF;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookDetailIdF", nullable = false)
    private BookDetails bookDetailIdF;

    @Column(name = "stock_type", columnDefinition = "VARCHAR(255) DEFAULT 'Default'")
    private String stockType;

}
