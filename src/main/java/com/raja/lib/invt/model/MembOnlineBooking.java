package com.raja.lib.invt.model;

import java.io.Serializable;

import com.raja.lib.auth.model.GeneralMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Data
@Entity
@Table(name = "memb_online_booking")
public class MembOnlineBooking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membOnlineId")
    private int membOnlineId;

    @Column(name = "invoiceNo")
    private String invoiceNo;

    @Column(name = "invoiceDate")
    private String invoiceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberIdF", referencedColumnName = "memberId")
    private GeneralMember generalMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_idF", referencedColumnName = "bookId")
    private Book book;
    
    @Column(name = "isBlock", columnDefinition = "char(1) default 'N'")
    private char isBlock; 
}
