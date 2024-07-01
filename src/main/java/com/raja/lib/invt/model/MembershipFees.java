package com.raja.lib.invt.model;

import com.raja.lib.auth.model.GeneralMember;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.List;

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
    
    @Column(name = "fess_total")
    private Double fess_total;

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

    @OneToMany(mappedBy = "membershipIdF", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MembershipFeesDetail> membershipFeesDetails;
}
