package com.raja.lib.invt.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.UserMember;
import com.raja.lib.invt.repository.UserMemberRepository;
import com.raja.lib.invt.request.UserMemberRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;

@Service
public class UserMemberService {

    private final UserMemberRepository userMemberRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserMemberService.class);

    public UserMemberService(UserMemberRepository userMemberRepository) {
        this.userMemberRepository = userMemberRepository;
    }

    public UserMember createUserMember(UserMemberRequestDTO requestDTO) {
        LOGGER.info("Creating user member");
        UserMember userMember = convertToEntity(requestDTO);
        return userMemberRepository.save(userMember);
    }

    public UserMember getUserMemberById(int id) {
        LOGGER.info("Fetching user member by id: {}", id);
        return userMemberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User member not found with id: " + id));
    }

    public List<UserMember> getAllUserMembers() {
        LOGGER.info("Fetching all user members");
        return userMemberRepository.findAll();
    }

    public UserMember updateUserMember(int id, UserMemberRequestDTO requestDTO) {
        LOGGER.info("Updating user member with id: {}", id);
        UserMember existingMember = getUserMemberById(id);
        existingMember.setFirstName(requestDTO.getFirstName());
        existingMember.setMiddleName(requestDTO.getMiddleName());
        existingMember.setLastName(requestDTO.getLastName());
        existingMember.setRegisterDate(requestDTO.getRegisterDate());
        existingMember.setAdharCard(requestDTO.getAdharCard());
        existingMember.setMemberAddress(requestDTO.getMemberAddress());
        existingMember.setDateOfBirth(requestDTO.getDateOfBirth());
        existingMember.setMemberEducation(requestDTO.getMemberEducation());
        existingMember.setMemberOccupation(requestDTO.getMemberOccupation());
        existingMember.setMobileNo(requestDTO.getMobileNo());
        existingMember.setMemberEmailId(requestDTO.getMemberEmailId());
        existingMember.setConfirmDate(requestDTO.getConfirmDate());

        return userMemberRepository.save(existingMember);
    }

    public ApiResponseDTO<String> deleteUserMember(int id) {
        LOGGER.info("Deleting user member with id: {}", id);
        UserMember existingMember = getUserMemberById(id);
        userMemberRepository.delete(existingMember);
        return new ApiResponseDTO<>(true, "User member deleted successfully", "User member with ID " + id + " has been deleted", HttpStatus.OK.value());
    }

    private UserMember convertToEntity(UserMemberRequestDTO requestDTO) {
        UserMember member = new UserMember();
        member.setFirstName(requestDTO.getFirstName());
        member.setMiddleName(requestDTO.getMiddleName());
        member.setLastName(requestDTO.getLastName());
        member.setRegisterDate(requestDTO.getRegisterDate());
        member.setAdharCard(requestDTO.getAdharCard());
        member.setMemberAddress(requestDTO.getMemberAddress());
        member.setDateOfBirth(requestDTO.getDateOfBirth());
        member.setMemberEducation(requestDTO.getMemberEducation());
        member.setMemberOccupation(requestDTO.getMemberOccupation());
        member.setMobileNo(requestDTO.getMobileNo());
        member.setMemberEmailId(requestDTO.getMemberEmailId());
        member.setConfirmDate(requestDTO.getConfirmDate());
        return member;
    }
}
