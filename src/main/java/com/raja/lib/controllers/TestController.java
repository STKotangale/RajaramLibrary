package com.raja.lib.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.DbService.PurchaseDbService;
import com.raja.lib.models.Purchase;
import com.raja.lib.payload.request.PurchaseRequestDto;
import com.raja.lib.payload.response.GetPurchaseReponseDto;
import com.raja.lib.payload.response.PurchaseResponseDto;
import com.raja.lib.security.services.PurchaseServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

//	@Autowired
//	private PurchaseDbService purchaseService;
//
//	@GetMapping("/all")
//	public String allAccess() {
//		return "Public Content.";
//	}
//
//	@GetMapping("/user")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//	public String userAccess() {
//		return "User Content.";
//	}
//
//	@GetMapping("/mod")
//	@PreAuthorize("hasRole('MODERATOR')")
//	public String moderatorAccess() {
//		return "Moderator Board.";
//	}
//
//	@GetMapping("/admin")
//	@PreAuthorize("hasRole('ADMIN')")
//	public String adminAccess() {
//		return "Admin Board.";
//	}
//
//	@PostMapping("/hello")
//	public String heloo() {
//		return "test";
//	}

}
