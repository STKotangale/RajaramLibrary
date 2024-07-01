package com.raja.lib.invt.model;

import com.raja.lib.auth.model.GeneralMember;
import com.raja.lib.acc.model.Ledger;
import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "acc_member_monthly_fees")
public class MemberMonthlyFees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberMonthlyId")
    private int memberMonthlyId;

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
    private int totalMonths;

    @Column(name = "feesType")
    private String feesType;

    @Column(name = "bankName")
    private String bankName;

    @Column(name = "chequeNo")
    private String chequeNo;

    @Column(name = "chequeDate")
    private String chequeDate;

    @Column(name = "monthlyDescription")
    private String monthlyDescription;
}
