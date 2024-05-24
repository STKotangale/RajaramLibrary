package com.raja.lib.invt.model;

import java.util.Date;
import com.raja.lib.acc.model.Ledger;
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
@Table(name = "acc_member_monthly_fees")
@Data
public class MemberMonthlyFees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberMonthlyId")
    private Long memberMonthlyId;

    @Column(name = "memMonInvoiceNo")
    private String memMonInvoiceNo;

    @Column(name = "memMonInvoiceDate")
    private String memMonInvoiceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberIdF", referencedColumnName = "memberId")
    private GeneralMember member;

    @Column(name = "feesAmount")
    private Double feesAmount;

    @Column(name = "fromDate")
    private String fromDate;

    @Column(name = "toDate")
    private String toDate;

    @Column(name = "totalMonths")
    private Integer totalMonths;

    @Column(name = "feesType")
    private String feesType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledgerIDF", referencedColumnName = "ledgerID")
    private Ledger ledger;

    @Column(name = "bankName")
    private String bankName;

    @Column(name = "chequeNo")
    private String chequeNo;

    @Column(name = "chequeDate")
    private String chequeDate;

    @Column(name = "monthlyDescription")
    private String monthlyDescription;
}