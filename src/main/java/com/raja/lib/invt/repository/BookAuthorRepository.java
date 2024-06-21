package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.BookAuthor;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Integer> {
	
	boolean existsByAuthorName(String authorName);
	
    boolean existsByAuthorNameAndAuthorIdNot(String authorName, int authorId);
}
