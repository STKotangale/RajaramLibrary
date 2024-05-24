package com.raja.lib.invt.model;

import java.util.Date;
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
import lombok.Data;

@Entity
@Table(name = "acc_membership_fees")
@Data
public class MembershipFees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membershipId")
    private Long membershipId;

    @Column(name = "mem_invoice_no")
    private String memInvoiceNo;

    @Column(name = "mem_invoice_date")
    private String memInvoiceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberIdF", referencedColumnName = "memberId")
    private GeneralMember member;

    @Column(name = "bookDeposit_Fees")
    private Double bookDepositFees;

    @Column(name = "entryFees")
    private Double entryFees;

    @Column(name = "securityDepositFees")
    private Double securityDepositFees;

    @Column(name = "feesType")
    private String feesType;

    @Column(name = "bankName")
    private String bankName;

    @Column(name = "chequeNo")
    private String chequeNo;

    @Column(name = "chequeDate")
    private String chequeDate;

    @Column(name = "membershipDescription")
    private String membershipDescription;
}
