package com.raja.lib.invt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "invt_lib_fees")
@Data
public class LibraryFee {

	@Column(name = "feesId")
	@Id
	private Long feesId;

	@Column(name = "feesName")
	private String feesName;

	@Column(name = "feesAmount")
	private int feesAmount;

	@Column(name = "isBlock")
	private String isBlock;

}
