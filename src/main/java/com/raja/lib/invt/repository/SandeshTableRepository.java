package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.SandeshTable;

@Repository
public interface SandeshTableRepository extends JpaRepository<SandeshTable, Integer> {
}