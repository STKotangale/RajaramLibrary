package com.raja.lib.invt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.raja.lib.invt.model.SandeshTable;
import com.raja.lib.invt.repository.SandeshTableRepository;
import com.raja.lib.invt.request.SandeshTableRequest;
import com.raja.lib.invt.resposne.ApiResponseDTO;

@Service
public class SandeshTableService {

    @Autowired
    private SandeshTableRepository sandeshTableRepository;

    public ApiResponseDTO<SandeshTable> createSandeshTable(SandeshTableRequest request) {
        try {
        	SandeshTable sandeshTable = new SandeshTable();
        	sandeshTable.setSandeshName(request.getSandeshName());
        	sandeshTable.setIsBlock(request.getIsBlock());
        	SandeshTable savedSandeshTable = sandeshTableRepository.save(sandeshTable);
            return new ApiResponseDTO<>(true, "Sandesh Table created successfully", savedSandeshTable, HttpStatus.CREATED.value());
        } catch (DataIntegrityViolationException e) {
            return new ApiResponseDTO<>(false, "Failed to create Sandesh Table. Sandesh name already exists.", null, HttpStatus.BAD_REQUEST.value());
        }
    }

    @Cacheable(value = "sandeshTables")
    public ApiResponseDTO<List<SandeshTable>> getAllSandeshTable() {
        List<SandeshTable> sandeshTables = sandeshTableRepository.findAll();
        return new ApiResponseDTO<>(true, "List of Sandesh Tables", sandeshTables, HttpStatus.OK.value());
    }

//    @Cacheable(value = "sandeshTableById", key = "#id")
    public ApiResponseDTO<SandeshTable> getSandeshTableById(int id) {
        Optional<SandeshTable> optionalSandeshTable = sandeshTableRepository.findById(id);
        if (optionalSandeshTable.isPresent()) {
            return new ApiResponseDTO<>(true, "Sandesh Table found", optionalSandeshTable.get(), HttpStatus.OK.value());
        } else {
            return new ApiResponseDTO<>(false, "Sandesh Table not found", null, HttpStatus.NOT_FOUND.value());
        }
    }

//    @Caching(put = {
//        @CachePut(value = "sandeshTableById", key = "#id")
//    }, evict = {
//        @CacheEvict(value = "sandeshTables", allEntries = true)
//    })
    public ApiResponseDTO<SandeshTable> updateSandeshTable(int id, SandeshTableRequest request) {
        Optional<SandeshTable> optionalSandeshTable = sandeshTableRepository.findById(id);
        if (optionalSandeshTable.isPresent()) {
        	SandeshTable existingSandeshTable = optionalSandeshTable.get();
        	existingSandeshTable.setSandeshName(request.getSandeshName());
        	existingSandeshTable.setIsBlock(request.getIsBlock());
        	SandeshTable updatedSandeshTable = sandeshTableRepository.save(existingSandeshTable);
            return new ApiResponseDTO<>(true, "Sandesh Table updated successfully", updatedSandeshTable, HttpStatus.OK.value());
        } else {
            return new ApiResponseDTO<>(false, "Sandesh Table not found", null, HttpStatus.NOT_FOUND.value());
        }
    }
 
//    @Caching(evict = {
//        @CacheEvict(value = "sandeshTables", allEntries = true),
//        @CacheEvict(value = "sandeshTableById", key = "#id")
//    })
    public ApiResponseDTO<Void> deleteSandeshTable(int id) {
        Optional<SandeshTable> optionalSandeshTable = sandeshTableRepository.findById(id);
        if (optionalSandeshTable.isPresent()) {
        	sandeshTableRepository.deleteById(id);
            return new ApiResponseDTO<>(true, "Sandesh Table deleted successfully", null, HttpStatus.OK.value());
        } else {
            return new ApiResponseDTO<>(false, "Sandesh Table not found", null, HttpStatus.NOT_FOUND.value());
        }
    }
    
    
}
