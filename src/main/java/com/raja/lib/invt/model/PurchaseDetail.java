package com.raja.lib.invt.model;

import java.io.Serializable;
import org.springframework.stereotype.Component;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="invt_purchase_detail")
public class PurchaseDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int purchaseDetailId;
	
	private int purchaseIdF;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int srno;
    private int book_idF;
	private int book_qty;
	private int book_rate;
	private int book_amount;

}