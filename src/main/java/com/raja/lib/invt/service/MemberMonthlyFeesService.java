package com.raja.lib.invt.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raja.lib.acc.model.Ledger;
import com.raja.lib.acc.repository.LedgerRepository;
import com.raja.lib.auth.model.GeneralMember;
import com.raja.lib.auth.repository.GeneralMemberRepository;
import com.raja.lib.invt.model.MemberMonthlyFees;
import com.raja.lib.invt.repository.MemberMonthlyFeesRepository;
import com.raja.lib.invt.request.MemberMonthlyFeesRequest;
import com.raja.lib.invt.resposne.MemberMonthlyFeesResponse;

@Service
public class MemberMonthlyFeesService {

    @Autowired
    private MemberMonthlyFeesRepository repository;

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private GeneralMemberRepository generalMemberRepository;

    public List<MemberMonthlyFeesResponse> getAllFees() {
        return repository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public MemberMonthlyFeesResponse getFeeById(Long id) {
        MemberMonthlyFees fee = repository.findById(id).orElseThrow(() -> new RuntimeException("Fee not found"));
        return convertToResponse(fee);
    }

    public MemberMonthlyFeesResponse createFee(MemberMonthlyFeesRequest request) {
        MemberMonthlyFees fee = new MemberMonthlyFees();
        updateEntityWithRequest(fee, request);
        return convertToResponse(repository.save(fee));
    }

    public MemberMonthlyFeesResponse updateFee(Long id, MemberMonthlyFeesRequest request) {
        MemberMonthlyFees fee = repository.findById(id).orElseThrow(() -> new RuntimeException("Fee not found"));
        updateEntityWithRequest(fee, request);
        return convertToResponse(repository.save(fee));
    }

    public void deleteFee(Long id) {
        repository.deleteById(id);
    }

    private void updateEntityWithRequest(MemberMonthlyFees fee, MemberMonthlyFeesRequest request) {
        fee.setMemMonInvoiceNo(request.getMemMonInvoiceNo());
        fee.setMemMonInvoiceDate(request.getMemMonInvoiceDate());
        fee.setFeesAmount(request.getFeesAmount());
        fee.setFromDate(request.getFromDate());
        fee.setToDate(request.getToDate());
        fee.setTotalMonths(request.getTotalMonths());
        fee.setFeesType(request.getFeesType());
        fee.setBankName(request.getBankName());
        fee.setChequeNo(request.getChequeNo());
        fee.setChequeDate(request.getChequeDate());
        fee.setMonthlyDescription(request.getMonthlyDescription());

        GeneralMember member = generalMemberRepository.findById(request.getMemberIdF())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        fee.setMember(member);

        Ledger ledger = ledgerRepository.findById(request.getLedgerIDF())
                .orElseThrow(() -> new RuntimeException("Ledger not found"));
        fee.setLedger(ledger);
    }

    private MemberMonthlyFeesResponse convertToResponse(MemberMonthlyFees fee) {
        MemberMonthlyFeesResponse response = new MemberMonthlyFeesResponse();
        response.setMemberMonthlyId(fee.getMemberMonthlyId());
        response.setMemMonInvoiceNo(fee.getMemMonInvoiceNo());
        response.setMemMonInvoiceDate(fee.getMemMonInvoiceDate());
        response.setMemberIdF(fee.getMember().getMemberId());
        response.setFeesAmount(fee.getFeesAmount());
        response.setFromDate(fee.getFromDate());
        response.setToDate(fee.getToDate());
        response.setTotalMonths(fee.getTotalMonths());
        response.setFeesType(fee.getFeesType());
        response.setLedgerIDF(fee.getLedger().getLedgerID());
        response.setBankName(fee.getBankName());
        response.setChequeNo(fee.getChequeNo());
        response.setChequeDate(fee.getChequeDate());
        response.setMonthlyDescription(fee.getMonthlyDescription());
        return response;
    }
}
