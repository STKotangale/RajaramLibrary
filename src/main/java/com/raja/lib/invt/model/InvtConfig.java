package com.raja.lib.invt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "invt_config")
@Data
public class InvtConfig {
	@Id
	@Column(name="srno")
	private Long srno;
	
	@Column(name="bookDays")
	private Integer bookDays;
	
	@Column(name="finePerDays")
	private Double finePerDays;

}
