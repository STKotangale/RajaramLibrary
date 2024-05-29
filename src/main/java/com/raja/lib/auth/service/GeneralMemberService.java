package com.raja.lib.auth.service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raja.lib.auth.model.GeneralMember;
import com.raja.lib.auth.model.Role;
import com.raja.lib.auth.model.User;
import com.raja.lib.auth.objects.BookIssueDetails;
import com.raja.lib.auth.objects.GenralMember;
import com.raja.lib.auth.objects.MemberBookInfo;
import com.raja.lib.auth.repository.GeneralMemberRepository;
import com.raja.lib.auth.repository.RoleRepository;
import com.raja.lib.auth.repository.UserRepository;
import com.raja.lib.auth.request.GeneralMemberRequestDTO;
import com.raja.lib.auth.request.PasswordUpdateRequestDTO;
import com.raja.lib.auth.request.UserCheckRequestDTO;
import com.raja.lib.invt.resposne.ApiResponseDTO;

import jakarta.transaction.Transactional;

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

	@Transactional
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
		Role memberRole = roleRepository.findById(2)
				.orElseThrow(() -> new NoSuchElementException("Role not found with ID 2"));
		Set<Role> roles = new HashSet<>();
		roles.add(memberRole);
		User user = new User(username, email, password, 'N');
		user.setGeneralMember(generalMember);
		user.setRoles(roles);
		userrepository.save(user);
	}

	public GenralMember getGeneralMemberById(int id) {
		LOGGER.info("Fetching general member by id: {}", id);
		return generalMemberRepository.getGeneralMemberById(id);
	}

	public List<GenralMember> getAllGeneralMembers() {
		LOGGER.info("Fetching all general members");
		return generalMemberRepository.getAllGenralMember();
	}

	public GeneralMember updateGeneralMember(int memberId, GeneralMemberRequestDTO requestDTO) {
		LOGGER.info("Updating general member with memberId: {}", memberId);
		GeneralMember existingMember = generalMemberRepository.findById(memberId)
				.orElseThrow(() -> new NoSuchElementException("General member not found with memberId: " + memberId));
		updateGeneralMemberDetails(existingMember, requestDTO);
		User user = userrepository.findByGeneralMember_MemberId(memberId).orElse(new User());
		updateUserDetails(user, requestDTO);
		user.setGeneralMember(existingMember);
		userrepository.save(user);
		return generalMemberRepository.save(existingMember);
	}

	private void updateGeneralMemberDetails(GeneralMember member, GeneralMemberRequestDTO requestDTO) {
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
		member.setIsBlock('N');
	}

	private void updateUserDetails(User user, GeneralMemberRequestDTO requestDTO) {
		user.setUsername(requestDTO.getUsername());
		user.setUseremail(requestDTO.getMemberEmailId());
		user.setUserpassword(passwordEncoder.encode(requestDTO.getPassword()));

		Role defaultRole = roleRepository.findById(2)
				.orElseThrow(() -> new NoSuchElementException("Default Role not found"));
		Set<Role> roles = new HashSet<>();
		roles.add(defaultRole);
		user.setRoles(roles);
	}

	public ApiResponseDTO<String> deleteGeneralMember(int id) {
		LOGGER.info("Deleting general member with id: {}", id);
		GeneralMember existingMember = generalMemberRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("General member not found with id: " + id));
		User user = userrepository.findByGeneralMember_MemberId(id).orElse(null);
		if (user != null) {
			userrepository.delete(user);
		}
		generalMemberRepository.delete(existingMember);
		return new ApiResponseDTO<>(true, "General member deleted successfully",
				"General member with ID " + id + " has been deleted", HttpStatus.OK.value());
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
		member.setIsBlock('N');
		return member;
	}

	public List<MemberBookInfo> getMemberBookInfo(int userId) {
	    return generalMemberRepository.findMemberBookInfoByUserId(userId);
	}


	public List<BookIssueDetails> getBookIssueDetails(int userId, String startDate, String endDate) {
	    return generalMemberRepository.findBookIssueDetails(userId, startDate, endDate);
	}
	
	public ApiResponseDTO<String> checkUserDetails(UserCheckRequestDTO requestDTO) {
        Optional<User> userOptional = userrepository.findByUseremail(requestDTO.getEmail());
        Optional<GeneralMember> memberOptional = generalMemberRepository.findByMobileNo(requestDTO.getMobileNo());

        if (userOptional.isPresent() && memberOptional.isPresent() && userOptional.get().getGeneralMember().equals(memberOptional.get())) {
            User user = userOptional.get();
            return new ApiResponseDTO<>(true, "User found",""+ user.getUserId(), HttpStatus.OK.value());
        } else {
            return new ApiResponseDTO<>(false, "User not found", "No user found with the provided email and mobile number", HttpStatus.NOT_FOUND.value());
        }
    }
	
	
	public ApiResponseDTO<String> updatePassword(PasswordUpdateRequestDTO requestDTO) {
        if (!requestDTO.getPassword().equals(requestDTO.getConfirmPassword())) {
            return new ApiResponseDTO<>(false, "Passwords do not match", null, HttpStatus.BAD_REQUEST.value());
        }

        Optional<User> userOptional = userrepository.findById(requestDTO.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUserpassword(passwordEncoder.encode(requestDTO.getPassword()));
            userrepository.save(user);
            return new ApiResponseDTO<>(true, "Password updated successfully", null, HttpStatus.OK.value());
        } else {
            return new ApiResponseDTO<>(false, "User not found", null, HttpStatus.NOT_FOUND.value());
        }
    }
	
	

}
