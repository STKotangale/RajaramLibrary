package com.raja.lib.invt.controller;

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

import com.raja.lib.invt.model.GeneralMember;
import com.raja.lib.invt.objects.GenralMember;
import com.raja.lib.invt.request.GeneralMemberRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.service.GeneralMemberService;

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
    public ResponseEntity<ApiResponseDTO<GeneralMember>> getGeneralMemberById(@PathVariable int id) {
        GeneralMember member = generalMemberService.getGeneralMemberById(id);
        ApiResponseDTO<GeneralMember> responseDTO = new ApiResponseDTO<>(true, "General member found", member, HttpStatus.OK.value());
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
}
