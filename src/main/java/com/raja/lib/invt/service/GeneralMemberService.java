package com.raja.lib.invt.service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raja.lib.auth.model.Role;
import com.raja.lib.auth.model.User;
import com.raja.lib.auth.repository.RoleRepository;
import com.raja.lib.auth.repository.UserRepository;
import com.raja.lib.invt.model.GeneralMember;
import com.raja.lib.invt.repository.GeneralMemberRepository;
import com.raja.lib.invt.request.GeneralMemberRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;

@Service
public class GeneralMemberService {

    private final GeneralMemberRepository generalMemberRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    
    @Autowired
    UserRepository userrepository;
    @Autowired
    RoleRepository roleRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralMemberService.class);

    public GeneralMemberService(GeneralMemberRepository generalMemberRepository) {
        this.generalMemberRepository = generalMemberRepository;
    }

    public GeneralMember createGeneralMember(GeneralMemberRequestDTO requestDTO) {
        LOGGER.info("Creating general member");

        GeneralMember generalMember = convertToEntity(requestDTO);

        GeneralMember savedMember = generalMemberRepository.save(generalMember);

        createUserFromGeneralMember(savedMember, requestDTO);

        return savedMember;
    }

    private void createUserFromGeneralMember(GeneralMember generalMember, GeneralMemberRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        String password = passwordEncoder.encode(requestDTO.getPassword());
        String email = requestDTO.getMemberEmailId();

        Role memberRole = roleRepository.findById((long) 2)
            .orElseThrow(() -> new NoSuchElementException("Role not found with ID 2"));
        Set<Role> roles = new HashSet<>();
        roles.add(memberRole);

        User user = new User(username, email, password, false, String.valueOf(generalMember.getMemberId()), roles);

        userrepository.save(user);
    }



    public GeneralMember getGeneralMemberById(int id) {
        LOGGER.info("Fetching general member by id: {}", id);
        return generalMemberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("General member not found with id: " + id));
    }

    public List<GeneralMember> getAllGeneralMembers() {
        LOGGER.info("Fetching all general members");
        return generalMemberRepository.findAll();
    }

    public GeneralMember updateGeneralMember(int id, GeneralMemberRequestDTO requestDTO) {
        LOGGER.info("Updating general member with id: {}", id);
        GeneralMember existingMember = getGeneralMemberById(id);
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
        existingMember.setConfirmDate(requestDTO.getConfirmDate());
        existingMember.setIsBlock(requestDTO.getIsBlock());

        return generalMemberRepository.save(existingMember);
    }

    public ApiResponseDTO<String> deleteGeneralMember(int id) {
        LOGGER.info("Deleting general member with id: {}", id);
        GeneralMember existingMember = getGeneralMemberById(id);
        generalMemberRepository.delete(existingMember);
        return new ApiResponseDTO<>(true, "General member deleted successfully", "General member with ID " + id + " has been deleted", HttpStatus.OK.value());
    }

    private GeneralMember convertToEntity(GeneralMemberRequestDTO requestDTO) {
        GeneralMember member = new GeneralMember();
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
        member.setConfirmDate(requestDTO.getConfirmDate());
        member.setIsBlock(requestDTO.getIsBlock());
        return member;
    }
}
