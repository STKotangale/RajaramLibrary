package com.raja.lib.invt.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PurchaseDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="purchase_detail_id")
	private Long purchaseDetail;
	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int srno;

	private String bookName;
	private int qty;
	private int rate;
	private int amount;

	@ManyToOne
	@JoinColumn(name = "purchase_idF", nullable = false)
	private Purchase purchase;
	
	
	@OneToMany(mappedBy = "purchaseDetail", cascade = CascadeType.ALL)
    private List<BookDetails> bookDetails; 
}
