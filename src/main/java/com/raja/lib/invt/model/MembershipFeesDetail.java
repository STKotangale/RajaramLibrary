package com.raja.lib.invt.model;

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
@Table(name = "acc_membership_fees_detail")
@Data
public class MembershipFeesDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membershipDetailId")
    private int membershipDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membershipIdF", referencedColumnName = "membershipId")
    private MembershipFees membershipIdF;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feesIdF", referencedColumnName = "feesId")
    private LibraryFee feesIdF;

    @Column(name = "feesAmount")
    private Double feesAmount;
}
