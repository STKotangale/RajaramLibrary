package com.raja.lib.invt.controller;

import java.util.List;

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

import com.raja.lib.auth.model.PermanentMember;
import com.raja.lib.invt.request.PermanentMemberRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.service.PermanentMemberService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/permanent-members")
public class PermanentMemberController {

    private final PermanentMemberService permanentMemberService;

    public PermanentMemberController(PermanentMemberService permanentMemberService) {
        this.permanentMemberService = permanentMemberService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<PermanentMember>> createPermanentMember(@RequestBody PermanentMemberRequestDTO requestDTO) {
        PermanentMember createdMember = permanentMemberService.createPermanentMember(requestDTO);
        ApiResponseDTO<PermanentMember> responseDTO = new ApiResponseDTO<>(true, "Permanent member created successfully", createdMember, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PermanentMember>> getPermanentMemberById(@PathVariable int id) {
        PermanentMember member = permanentMemberService.getPermanentMemberById(id);
        ApiResponseDTO<PermanentMember> responseDTO = new ApiResponseDTO<>(true, "Permanent member found", member, HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<PermanentMember>>> getAllPermanentMembers() {
        List<PermanentMember> members = permanentMemberService.getAllPermanentMembers();
        ApiResponseDTO<List<PermanentMember>> responseDTO = new ApiResponseDTO<>(true, "All permanent members retrieved", members, HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PermanentMember>> updatePermanentMember(@PathVariable int id, @RequestBody PermanentMemberRequestDTO requestDTO) {
        PermanentMember updatedMember = permanentMemberService.updatePermanentMember(id, requestDTO);
        ApiResponseDTO<PermanentMember> responseDTO = new ApiResponseDTO<>(true, "Permanent member updated successfully", updatedMember, HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deletePermanentMember(@PathVariable int id) {
        ApiResponseDTO<String> responseDTO = permanentMemberService.deletePermanentMember(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


}
