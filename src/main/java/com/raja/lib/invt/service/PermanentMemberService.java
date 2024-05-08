package com.raja.lib.invt.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.PermanentMember;
import com.raja.lib.invt.repository.PermanentMemberRepository;
import com.raja.lib.invt.request.PermanentMemberRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;

@Service
public class PermanentMemberService {

    private final PermanentMemberRepository permanentMemberRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PermanentMemberService.class);

    public PermanentMemberService(PermanentMemberRepository permanentMemberRepository) {
        this.permanentMemberRepository = permanentMemberRepository;
    }

    public PermanentMember createPermanentMember(PermanentMemberRequestDTO requestDTO) {
        LOGGER.info("Creating permanent member");
        PermanentMember permanentMember = convertToEntity(requestDTO);
        return permanentMemberRepository.save(permanentMember);
    }

    public PermanentMember getPermanentMemberById(int id) {
        LOGGER.info("Fetching permanent member by id: {}", id);
        return permanentMemberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Permanent member not found with id: " + id));
    }

    public List<PermanentMember> getAllPermanentMembers() {
        LOGGER.info("Fetching all permanent members");
        return permanentMemberRepository.findAll();
    }

    public PermanentMember updatePermanentMember(int id, PermanentMemberRequestDTO requestDTO) {
        LOGGER.info("Updating permanent member with id: {}", id);
        PermanentMember existingMember = getPermanentMemberById(id);
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
        

        return permanentMemberRepository.save(existingMember);
    }
    public ApiResponseDTO<String> deletePermanentMember(int id) {
        LOGGER.info("Deleting permanent member with id: {}", id);
        PermanentMember existingMember = getPermanentMemberById(id);
        permanentMemberRepository.delete(existingMember);
        return new ApiResponseDTO<>(true, "Permanent member deleted successfully", "Permanent member with ID " + id + " has been deleted", HttpStatus.OK.value());
    }



    private PermanentMember convertToEntity(PermanentMemberRequestDTO requestDTO) {
        PermanentMember member = new PermanentMember();
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
