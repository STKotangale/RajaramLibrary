package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.InvtConfig;

@Repository
public interface InvtConfigRepository extends JpaRepository<InvtConfig, Integer> {
	
    InvtConfig findFirstByOrderBySrnoAsc();

}