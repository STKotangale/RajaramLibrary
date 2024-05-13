package com.raja.lib.invt.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="invt_stock_copy_no")
public class StockCopyNo implements Serializable{
    
    private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="stockCopyId")
        private int stockCopyId;
        
        @ManyToOne
        @JoinColumn(name="stockDetailIdF")
        private Stock stockDetailIdF;
        
        @ManyToOne
        @JoinColumn(name="bookDetailIdF")
        private Book bookDetailIdF;
        
        @Column(name="stock_type")
        private String stock_type;

        
}
