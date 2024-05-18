package com.raja.lib.invt.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.raja.lib.acc.model.Ledger;
import com.raja.lib.auth.model.GeneralMember;

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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
@Entity
@Table(name = "invt_stock")
public class Stock implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stock_id")
	private int stockId;

	@Column(name = "stock_type", columnDefinition = "VARCHAR(255) DEFAULT 'A1'")
	private String stock_type = "A1";

	@Column(name = "invoiceNo")
	private String invoiceNo;

	@Column(name = "invoiceDate")
	private String invoiceDate;

	@Column(name = "billTotal")
	private double billTotal;

	@Column(name = "discountPercent")
	private double discountPercent;

	@Column(name = "discountAmount")
	private double discountAmount;

	@Column(name = "totalAfterDiscount")
	private double totalAfterDiscount;

	@Column(name = "gstPercent")
	private double gstPercent;

	@Column(name = "gstAmount")
	private double gstAmount;

	@Column(name = "grandTotal")
	private double grandTotal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ledgerIDF", nullable = false)
	private Ledger ledgerIDF;

	@OneToMany(mappedBy = "stockIdF", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StockDetail> stockDetails;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberIdF")
	private GeneralMember generalMember;

}