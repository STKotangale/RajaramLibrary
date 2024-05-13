package com.raja.lib.invt.repository;

import com.raja.lib.invt.model.StockDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDetailRepository extends JpaRepository<StockDetail, Integer> {
	
	@Query(value="SELECT MAX(is2.srno) FROM invt_stockdetail is2",nativeQuery = true)
    Integer findMaxSrno();
}
