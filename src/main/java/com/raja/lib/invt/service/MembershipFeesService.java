package com.raja.lib.invt.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.raja.lib.auth.model.GeneralMember;
import com.raja.lib.auth.repository.GeneralMemberRepository;
import com.raja.lib.invt.model.MembershipFees;
import com.raja.lib.invt.repository.MemberMonthlyFeesRepository;
import com.raja.lib.invt.repository.MembershipFeesRepository;
import com.raja.lib.invt.request.MemberCheckRequestDTO;
import com.raja.lib.invt.request.MembershipFeesRequest;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.resposne.MembershipFeesResponse;

@Service
public class MembershipFeesService {

    @Autowired
    private MembershipFeesRepository repository;

    @Autowired
    private GeneralMemberRepository generalMemberRepository;
    
    @Autowired
    private MemberMonthlyFeesRepository memberMonthlyFeesRepository;
    
    @Autowired
    private MembershipFeesRepository membershipFeesRepository;
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");


    public List<MembershipFeesResponse> getAllFees() {
        return repository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public MembershipFeesResponse getFeeById(Long id) {
        MembershipFees fee = repository.findById(id).orElseThrow(() -> new RuntimeException("Fee not found"));
        return convertToResponse(fee);
    }

    public MembershipFeesResponse createFee(MembershipFeesRequest request) {
        MembershipFees fee = new MembershipFees();
        updateEntityWithRequest(fee, request);
        return convertToResponse(repository.save(fee));
    }

    public MembershipFeesResponse updateFee(Long id, MembershipFeesRequest request) {
        MembershipFees fee = repository.findById(id).orElseThrow(() -> new RuntimeException("Fee not found"));
        updateEntityWithRequest(fee, request);
        return convertToResponse(repository.save(fee));
    }

    public void deleteFee(Long id) {
        repository.deleteById(id);
    }

    private void updateEntityWithRequest(MembershipFees fee, MembershipFeesRequest request) {
        fee.setMemInvoiceNo(request.getMemInvoiceNo());
        fee.setMemInvoiceDate(request.getMemInvoiceDate());
        fee.setBookDepositFees(request.getBookDepositFees());
        fee.setEntryFees(request.getEntryFees());
        fee.setSecurityDepositFees(request.getSecurityDepositFees());
        fee.setFeesType(request.getFeesType());
        fee.setBankName(request.getBankName());
        fee.setChequeNo(request.getChequeNo());
        fee.setChequeDate(request.getChequeDate());
        fee.setMembershipDescription(request.getMembershipDescription());

        GeneralMember member = generalMemberRepository.findById(request.getMemberIdF())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        fee.setMember(member);
    }

    private MembershipFeesResponse convertToResponse(MembershipFees fee) {
        MembershipFeesResponse response = new MembershipFeesResponse();
        response.setMembershipId(fee.getMembershipId());
        response.setMemInvoiceNo(fee.getMemInvoiceNo());
        response.setMemInvoiceDate(fee.getMemInvoiceDate());
        response.setMemberIdF(fee.getMember().getMemberId());
        response.setBookDepositFees(fee.getBookDepositFees());
        response.setEntryFees(fee.getEntryFees());
        response.setSecurityDepositFees(fee.getSecurityDepositFees());
        response.setFeesType(fee.getFeesType());
        response.setBankName(fee.getBankName());
        response.setChequeNo(fee.getChequeNo());
        response.setChequeDate(fee.getChequeDate());
        response.setMembershipDescription(fee.getMembershipDescription());
        return response;
    }
    
    
    
    
    public ApiResponseDTO<String> checkMemberAndDate(MemberCheckRequestDTO request) {
        Optional<GeneralMember> memberOptional = generalMemberRepository.findById(request.getMemberId());

        if (!memberOptional.isPresent()) {
            return new ApiResponseDTO<>(false, "Member not found", null, HttpStatus.NOT_FOUND.value());
        }

        GeneralMember member = memberOptional.get();

        boolean monthlyFeeExists = false;
        boolean membershipFeeExists = false;

        try {
            String date = DATE_FORMAT.format(DATE_FORMAT.parse(request.getDate()));

            monthlyFeeExists = memberMonthlyFeesRepository.existsByMemberAndDate(request.getMemberId(), date);
            membershipFeeExists = membershipFeesRepository.existsByMemberAndDate(request.getMemberId(), date);
        } catch (ParseException e) {
            return new ApiResponseDTO<>(false, "Invalid date format", null, HttpStatus.BAD_REQUEST.value());
        }

        if (!monthlyFeeExists && !membershipFeeExists) {
            return new ApiResponseDTO<>(false, "Member does not have fees within the specified date range", null, HttpStatus.NOT_FOUND.value());
        }

        return new ApiResponseDTO<>(true, "Member has fees within the specified date range", null, HttpStatus.OK.value());
    }
}
