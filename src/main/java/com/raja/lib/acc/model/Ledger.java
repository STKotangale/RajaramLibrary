package com.raja.lib.acc.model;
import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
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
@Table(name = "acc_ledger")
public class Ledger implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ledgerID")
    private Long ledgerID;
    
    @Column(name="ledgerName")
    private String ledgerName;
    
    @Column(name="isBlock")
    private char isBlock; 

   
}