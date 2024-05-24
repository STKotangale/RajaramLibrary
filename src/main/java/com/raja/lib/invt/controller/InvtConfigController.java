package com.raja.lib.invt.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.invt.request.InvtConfigRequest;
import com.raja.lib.invt.resposne.InvtConfigResponse;
import com.raja.lib.invt.service.InvtConfigService;

@RestController
@RequestMapping("/api/config")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InvtConfigController {

	@Autowired
	private InvtConfigService invtConfigService;

	@GetMapping
	public ResponseEntity<List<InvtConfigResponse>> getAllConfig() {
		return ResponseEntity.ok(invtConfigService.getAllConfig());
	}

	@PutMapping("/{srno}")
	public ResponseEntity<InvtConfigResponse> updateConfig(@PathVariable Long srno,
			@RequestBody InvtConfigRequest request) {
		return ResponseEntity.ok(invtConfigService.updateConfig(srno, request));
	}
}
