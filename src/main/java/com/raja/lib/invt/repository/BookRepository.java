package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.raja.lib.invt.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
