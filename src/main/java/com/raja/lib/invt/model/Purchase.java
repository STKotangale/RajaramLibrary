package com.raja.lib.invt.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.raja.lib.acc.model.Ledger;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long purchaseId;

	private String invoiceNo;
	private Date invoiceDate;
	private int billTotal;
	private int discountPercent;
	private int discountAmount;
	private int totalAfterDiscount;
	private int gstPercent;
	private int gstAmount;
	private int grandTotal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ledger_idF")
	private Ledger ledger;

	@OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
	private List<PurchaseDetail> purchaseDetails;
	
	
	
	
}
