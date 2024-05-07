package com.raja.lib.acc.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="acc_ledger")
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ledgerID")
    private Long ledgerID;
    
    @Column(name="ledgerName")
    private String ledgerName;
    
    @Column(name="isBlock")
    private char isBlock; 

   
}
