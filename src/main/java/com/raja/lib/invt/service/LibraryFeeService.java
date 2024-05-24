package com.raja.lib.invt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.LibraryFee;
import com.raja.lib.invt.repository.LibraryFeeRepository;
import com.raja.lib.invt.request.LibraryFeeRequest;
import com.raja.lib.invt.resposne.LibraryFeeResponse;
import com.rajalib.lib.exception.ResourceNotFoundException;

@Service
public class LibraryFeeService {

    @Autowired
    private LibraryFeeRepository repository;

    public List<LibraryFeeResponse> getAllFees() {
        return repository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public LibraryFeeResponse updateFee(int feesId, LibraryFeeRequest request) {
        LibraryFee fee = repository.findById(feesId).orElseThrow(() -> new ResourceNotFoundException("Fee not found"));
        fee.setFeesName(request.getFeesName());
        fee.setFeesAmount(request.getFeesAmount());
        fee.setIsBlock(request.getIsBlock());
        repository.save(fee);
        return convertToResponse(fee);
    }

    private LibraryFeeResponse convertToResponse(LibraryFee fee) {
        LibraryFeeResponse response = new LibraryFeeResponse();
        response.setFeesId(fee.getFeesId());
        response.setFeesName(fee.getFeesName());
        response.setFeesAmount(fee.getFeesAmount());
        response.setIsBlock(fee.getIsBlock());
        return response;
    }
}

