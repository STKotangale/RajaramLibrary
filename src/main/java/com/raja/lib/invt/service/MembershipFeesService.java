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
import com.raja.lib.invt.model.LibraryFee;
import com.raja.lib.invt.model.MembershipFees;
import com.raja.lib.invt.model.MembershipFeesDetail;
import com.raja.lib.invt.repository.LibraryFeeRepository;
import com.raja.lib.invt.repository.MemberMonthlyFeesRepository;
import com.raja.lib.invt.repository.MembershipFeesDetailRepository;
import com.raja.lib.invt.repository.MembershipFeesRepository;
import com.raja.lib.invt.request.MemberCheckRequestDTO;
import com.raja.lib.invt.request.MembershipFeesDetailRequest;
import com.raja.lib.invt.request.MembershipFeesRequest;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.resposne.MembershipFeesDetailResponse;
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
    private MembershipFeesDetailRepository membershipFeesDetailRepository;

    @Autowired
    private LibraryFeeRepository libraryFeeRepository;
   
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public List<MembershipFeesResponse> getAllFees() {
        return repository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public MembershipFeesResponse getFeeById(Long id) {
        MembershipFees fee = repository.findById(id).orElseThrow(() -> new RuntimeException("Fee not found"));
        return convertToResponse(fee);
    }

    public ApiResponseDTO<String> createFee(MembershipFeesRequest request) {
        MembershipFees fee = new MembershipFees();
        updateEntityWithRequest(fee, request);
        MembershipFees savedFee = repository.save(fee);

        for (MembershipFeesDetail detail : fee.getMembershipFeesDetails()) {
            detail.setMembershipIdF(savedFee);
            membershipFeesDetailRepository.save(detail);
        }

        return new ApiResponseDTO<>(true, "Membership fee created successfully", null, HttpStatus.CREATED.value());
    }

    public ApiResponseDTO<String> updateFee(Long id, MembershipFeesRequest request) {
        MembershipFees fee = repository.findById(id).orElseThrow(() -> new RuntimeException("Fee not found"));
        updateEntityWithRequest(fee, request);
        repository.save(fee);
        return new ApiResponseDTO<>(true, "Membership fee updated successfully", null, HttpStatus.OK.value());
    }

    public ApiResponseDTO<String> deleteFee(Long id) {
        repository.deleteById(id);
        return new ApiResponseDTO<>(true, "Membership fee deleted successfully", null, HttpStatus.NO_CONTENT.value());
    }

    private void updateEntityWithRequest(MembershipFees fee, MembershipFeesRequest request) {
        fee.setMemInvoiceNo(request.getMemInvoiceNo());
        fee.setMemInvoiceDate(request.getMemInvoiceDate());
        fee.setFeesType(request.getFeesType());
        fee.setBankName(request.getBankName());
        fee.setChequeNo(request.getChequeNo());
        fee.setChequeDate(request.getChequeDate());
        fee.setMembershipDescription(request.getMembershipDescription());
        fee.setFess_total(request.getFess_total());

        GeneralMember member = generalMemberRepository.findById(request.getMemberIdF())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        fee.setMember(member);

        List<MembershipFeesDetail> details = request.getMembershipFeesDetails().stream().map(this::convertToDetailEntity)
                .collect(Collectors.toList());
        fee.setMembershipFeesDetails(details);
    }

    private MembershipFeesDetail convertToDetailEntity(MembershipFeesDetailRequest detailRequest) {
        MembershipFeesDetail detailEntity = new MembershipFeesDetail();
        detailEntity.setFeesAmount(detailRequest.getFeesAmount());

        LibraryFee libraryFee = libraryFeeRepository.findById(detailRequest.getFeesIdF())
                .orElseThrow(() -> new RuntimeException("Library fee not found"));
        detailEntity.setFeesIdF(libraryFee);

        return detailEntity;
    }

    private MembershipFeesResponse convertToResponse(MembershipFees fee) {
        MembershipFeesResponse response = new MembershipFeesResponse();
        response.setMembershipId(fee.getMembershipId());
        response.setMemInvoiceNo(fee.getMemInvoiceNo());
        response.setMemInvoiceDate(fee.getMemInvoiceDate());
        response.setMemberIdF(fee.getMember().getMemberId());
        response.setFirstName(fee.getMember().getFirstName()); // Added line
        response.setMiddleName(fee.getMember().getMiddleName()); // Added line
        response.setLastName(fee.getMember().getLastName()); // Added line
        response.setFeesType(fee.getFeesType());
        response.setFess_total(fee.getFess_total());
        response.setBankName(fee.getBankName());
        response.setChequeNo(fee.getChequeNo());
        response.setChequeDate(fee.getChequeDate());
        response.setMembershipDescription(fee.getMembershipDescription());
        response.setMembershipFeesDetails(fee.getMembershipFeesDetails().stream().map(this::convertToDetailResponse)
                .collect(Collectors.toList()));
        return response;
    }

    private MembershipFeesDetailResponse convertToDetailResponse(MembershipFeesDetail detail) {
        MembershipFeesDetailResponse response = new MembershipFeesDetailResponse();
        response.setMembershipDetailId(detail.getMembershipDetailId());
        response.setFeesIdF(detail.getFeesIdF().getFeesId());
        response.setFeesAmount(detail.getFeesAmount());
        return response;
    }

    public ApiResponseDTO<String> checkMemberAndDate(MemberCheckRequestDTO request) {
        Optional<GeneralMember> memberOptional = generalMemberRepository.findById(request.getMemberId());

        if (!memberOptional.isPresent()) {
            return new ApiResponseDTO<>(false, "Member not found", null, HttpStatus.NOT_FOUND.value());
        }

        String dateStr;
        try {
            dateStr = DATE_FORMAT.format(DATE_FORMAT.parse(request.getDate()));
        } catch (ParseException e) {
            return new ApiResponseDTO<>(false, "Invalid date format", null, HttpStatus.BAD_REQUEST.value());
        }

        boolean monthlyFeeExists = memberMonthlyFeesRepository.existsByMemberAndDate(request.getMemberId(), dateStr);
        boolean membershipFeeExists = memberMonthlyFeesRepository.existsByMemberAndDate(request.getMemberId(), dateStr);

        if (!monthlyFeeExists && !membershipFeeExists) {
            return new ApiResponseDTO<>(false, "Member does not have fees within the specified date range", null, HttpStatus.NOT_FOUND.value());
        }

        return new ApiResponseDTO<>(true, "Member has fees within the specified date range", null, HttpStatus.OK.value());
    }
    
    public int getNextInvoiceNumber() {
        int maxInvoiceNumber = memberMonthlyFeesRepository.getNextInvoiceNumber();
        return maxInvoiceNumber > 0 ? maxInvoiceNumber : 1;
    }
    
    public int getNextInvoiceMembershipNo() {
        int maxInvoiceNumber = repository.getNextMembershipNo();
        return maxInvoiceNumber > 0 ? maxInvoiceNumber : 1;
    }

}
