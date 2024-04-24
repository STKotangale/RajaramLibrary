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

import com.raja.lib.invt.model.UserMember;
import com.raja.lib.invt.request.UserMemberRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;
import com.raja.lib.invt.service.UserMemberService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user-members")
public class UserMemberController {

    private final UserMemberService userMemberService;

    public UserMemberController(UserMemberService userMemberService) {
        this.userMemberService = userMemberService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<UserMember>> createUserMember(@RequestBody UserMemberRequestDTO requestDTO) {
        UserMember userMember = userMemberService.createUserMember(requestDTO);
        ApiResponseDTO<UserMember> responseDTO = new ApiResponseDTO<>(true, "User member created successfully", userMember, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserMember>> getUserMemberById(@PathVariable int id) {
        UserMember userMember = userMemberService.getUserMemberById(id);
        ApiResponseDTO<UserMember> responseDTO = new ApiResponseDTO<>(true, "User member retrieved successfully", userMember, HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<UserMember>>> getAllUserMembers() {
        List<UserMember> userMembers = userMemberService.getAllUserMembers();
        ApiResponseDTO<List<UserMember>> responseDTO = new ApiResponseDTO<>(true, "User members retrieved successfully", userMembers, HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserMember>> updateUserMember(@PathVariable int id, @RequestBody UserMemberRequestDTO requestDTO) {
        UserMember userMember = userMemberService.updateUserMember(id, requestDTO);
        ApiResponseDTO<UserMember> responseDTO = new ApiResponseDTO<>(true, "User member updated successfully", userMember, HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<String>> deleteUserMember(@PathVariable int id) {
        ApiResponseDTO<String> responseDTO = userMemberService.deleteUserMember(id);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }
}
