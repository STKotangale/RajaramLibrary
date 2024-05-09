package com.raja.lib.invt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raja.lib.invt.model.BookPublication;

@Repository
public interface BookPublicationRepository extends JpaRepository<BookPublication, Integer> {
    
    boolean existsByPublicationName(String publicationName);
}
