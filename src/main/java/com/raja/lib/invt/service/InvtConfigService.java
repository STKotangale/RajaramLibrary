package com.raja.lib.invt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.InvtConfig;
import com.raja.lib.invt.repository.InvtConfigRepository;
import com.raja.lib.invt.request.InvtConfigRequest;
import com.raja.lib.invt.resposne.InvtConfigResponse;

@Service
public class InvtConfigService {

	@Autowired
	private InvtConfigRepository invtConfigRepository;

	public List<InvtConfigResponse> getAllConfig() {
		return invtConfigRepository.findAll().stream().map(this::convertToResponse).collect(Collectors.toList());
	}

	public InvtConfigResponse updateConfig(Long srno, InvtConfigRequest request) {
		InvtConfig invtConfig = invtConfigRepository.findById(srno)
				.orElseThrow(() -> new RuntimeException("Config not found"));
		invtConfig.setBookDays(request.getBookDays());
		invtConfig.setFinePerDays(request.getFinePerDays());
		invtConfigRepository.save(invtConfig);
		return convertToResponse(invtConfig);
	}

	private InvtConfigResponse convertToResponse(InvtConfig invtConfig) {
		InvtConfigResponse response = new InvtConfigResponse();
		response.setSrno(invtConfig.getSrno());
		response.setBookDays(invtConfig.getBookDays());
		response.setFinePerDays(invtConfig.getFinePerDays());
		return response;
	}
}
