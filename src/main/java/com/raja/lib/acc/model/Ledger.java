package com.raja.lib.acc.model;
import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ledger implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ledgerID")
    private int ledgerID;
    
    @Column(name="ledgerName")
    private String ledgerName;
    
    @Column(name = "isBlock", columnDefinition = "char(1) default 'N'")
    private char isBlock; 

   
}