	package com.raja.lib.invt.model;
	
	import java.io.Serializable;
	import java.util.List;
	
	import org.springframework.stereotype.Component;
	
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
	@Table(name = "invt_stockdetail")
	public class StockDetail implements Serializable {
	
		private static final long serialVersionUID = 1L;
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "stockDetailId")
		private int stockDetailId;
		
		 @OneToMany(mappedBy = "stockDetailIdF", cascade = CascadeType.ALL, orphanRemoval = true)
		 private List<BookDetails> bookDetails;
	
		@ManyToOne
	    @JoinColumn(name = "stock_idF", nullable = false)
	    private Stock stockIdF;
	
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "srno")
		private int srno;
	
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "book_idF", nullable = false)
		private Book book_idF;
		
		@Column(name = "book_qty")
		private int book_qty;
	
		@Column(name = "book_rate")
		private int book_rate;
	
		@Column(name = "book_amount")
		private int book_amount;
	
		@Column(name = "stock_type")
		private String stock_type = "A1";
		
		@Column(name="ref_issue_stockDetailId")
		private int ref_issue_stockDetailId;
	
		@Column(name="ref_issue_date")
		private String ref_issue_date;
		
		@Column(name="fineDays")
		private double fineDays;
		
		@Column(name="finePerDays")
		private double finePerDays;

		@Column(name="fineAmount")
		private double fineAmount;		
		
		@OneToMany(mappedBy = "stockDetailIdF", cascade = CascadeType.ALL, orphanRemoval = true)
		private List<StockCopyNo> stockCopyNos;
		
	}