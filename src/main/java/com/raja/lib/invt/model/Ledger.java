package com.raja.lib.invt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Ledger {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer ledgerId;
	    private String ledgerCode;
	    private String ledgerName;
	    
	 
	   
}
