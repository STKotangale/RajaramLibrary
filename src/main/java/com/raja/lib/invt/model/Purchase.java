package com.raja.lib.invt.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.raja.lib.acc.model.Ledger;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="invt_purchase")
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="purchaseId")
	private Long purchaseId;

	@Column(name="invoiceNo")
	private String invoiceNo;
	
	@Column(name="invoiceDate")
	private String invoiceDate;
	
	@Column(name="billTotal")
	private int billTotal;
	
	@Column(name="discountPercent")
	private int discountPercent;
	
	@Column(name="discountAmount")
	private int discountAmount;
	
	@Column(name="totalAfterDiscount")
	private int totalAfterDiscount;
	
	@Column(name="gstPercent")
	private int gstPercent;
	
	@Column(name="gstAmount")
	private int gstAmount;
	
	@Column(name="grandTotal")
	private int grandTotal;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledgerIDF", nullable = false)
	private Ledger ledger;

	@OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseDetail> purchaseDetails;
	
}
