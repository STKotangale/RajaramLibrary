package com.raja.lib.invt.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="invt_purchase_detail")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PurchaseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="purchaseDetailId")
    private Long purchaseDetailId;
    
    @Column(name="srno")
    private int srno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_idF", nullable = false) // Adjusted to use camelCase
    private Book book;

    @Column(name="book_qty")
    private int qty;
    
    @Column(name="book_rate")
    private double rate;
    
    @Column(name="book_amount")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "purchaseIdF", nullable = false)
    private Purchase purchase;
    
    @OneToMany(mappedBy = "purchaseDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookDetails> bookDetails;

    public void setBook(Book book) {
        this.book = book;
    }
}

