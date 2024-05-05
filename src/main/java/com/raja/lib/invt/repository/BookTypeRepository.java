package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.raja.lib.invt.model.BookType;

@Repository
public interface BookTypeRepository extends JpaRepository<BookType, Long> {
}
