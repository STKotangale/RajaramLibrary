package com.raja.lib.auth.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.auth.model.GeneralMember;
import com.raja.lib.auth.objects.BookIssueDetails;
import com.raja.lib.auth.objects.GenralMember;
import com.raja.lib.auth.objects.MemberBookInfo;
import com.raja.lib.auth.request.BookIssueDetailsRequest;
import com.raja.lib.auth.request.GeneralMemberRequestDTO;
import com.raja.lib.auth.request.PasswordUpdateRequestDTO;
import com.raja.lib.auth.request.UserCheckRequestDTO;
import com.raja.lib.auth.service.GeneralMemberService;
import com.raja.lib.invt.resposne.ApiResponseDTO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/general-members")
public class GeneralMemberController {

	@Autowired
    private final GeneralMemberService generalMemberService;

    public GeneralMemberController(GeneralMemberService generalMemberService) {
        this.generalMemberService = generalMemberService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<GeneralMember>> createGeneralMember(@RequestBody GeneralMemberRequestDTO requestDTO) {
        GeneralMember createdMember = generalMemberService.createGeneralMember(requestDTO);
        ApiResponseDTO<GeneralMember> responseDTO = new ApiResponseDTO<>(true, "General member created successfully", createdMember, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<GenralMember>> getGeneralMemberById(@PathVariable int id) {
    	GenralMember member = generalMemberService.getGeneralMemberById(id);
        ApiResponseDTO<GenralMember> responseDTO = new ApiResponseDTO<>(true, "General member found", member, HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<GenralMember>>> getAllGeneralMembers() {
        List<GenralMember> members = generalMemberService.getAllGeneralMembers();
        ApiResponseDTO<List<GenralMember>> responseDTO = new ApiResponseDTO<>(true, "All general members retrieved", members, HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<GeneralMember>> updateGeneralMember(@PathVariable int id, @RequestBody GeneralMemberRequestDTO requestDTO) {
        GeneralMember updatedMember = generalMemberService.updateGeneralMember(id, requestDTO);
        ApiResponseDTO<GeneralMember> responseDTO = new ApiResponseDTO<>(true, "General member updated successfully", updatedMember, HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deleteGeneralMember(@PathVariable int id) {
        ApiResponseDTO<String> responseDTO = generalMemberService.deleteGeneralMember(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
    
    @GetMapping("/memberBookInfo/{userId}")
    public List<MemberBookInfo> getMemberBookInfo(@PathVariable int userId) {
        return generalMemberService.getMemberBookInfo(userId);
    }

    
    
    @PostMapping("/bookIssueDetails")
    public List<BookIssueDetails> getBookIssueDetails(@RequestBody BookIssueDetailsRequest request) {
        return generalMemberService.getBookIssueDetails(request.getUserId(), request.getStartDate(), request.getEndDate());
    }

    @PostMapping("/check-user")
    public ResponseEntity<ApiResponseDTO<String>> checkUserDetails(@RequestBody UserCheckRequestDTO requestDTO) {
        ApiResponseDTO<String> responseDTO = generalMemberService.checkUserDetails(requestDTO);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PostMapping("/update-password")
    public ResponseEntity<ApiResponseDTO<String>> updatePassword(@RequestBody PasswordUpdateRequestDTO requestDTO) {
        ApiResponseDTO<String> responseDTO = generalMemberService.updatePassword(requestDTO);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }
}
