//package com.raja.lib.invt.model;
//
//import java.io.Serializable;
//import java.util.List;
//import org.springframework.stereotype.Component;
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//@Component
//@Data
//@Entity
//@Table(name="invt_purchase_detail")
//public class PurchaseDetail implements Serializable {
//	
//	private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="purchaseDetailId")
//    private int purchaseDetailId;
//    
//    @ManyToOne
//    @JoinColumn(name = "purchaseIdF", nullable = false)
//    private Purchase purchaseIdF;
//    
//    @Column(name="srno")
//    private int srno;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "book_idF", nullable = false) 
//    private Book book_idF;
//
//    @Column(name="book_qty")
//    private int book_qty;
//    
//    @Column(name="book_rate")
//    private double book_rate;
//    
//    @Column(name="book_amount")
//    private double book_amount;
//
//    @OneToMany(mappedBy = "purchaseDetail", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<BookDetails> bookDetails;
//
//    public void setBook(Book book_idF) {
//        this.book_idF = book_idF;
//    }
//}
//
