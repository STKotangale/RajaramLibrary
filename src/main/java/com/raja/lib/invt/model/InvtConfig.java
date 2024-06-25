package com.raja.lib.invt.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "invt_config")
@Data
public class InvtConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "srno")
	private int srno;

	@Column(name = "bookDays")
	private Integer bookDays;

	@Column(name = "finePerDays")
	private Double finePerDays;

	@Column(name="monthlyFees")
	private Double monthlyFees;
} 
