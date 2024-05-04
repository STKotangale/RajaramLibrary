package com.raja.lib.acc.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ledgerID;
    
    private String ledgerName;
    
    private char isBlock; 

    public Ledger(String ledgerName) {
        this.ledgerName = ledgerName;
    }
}
