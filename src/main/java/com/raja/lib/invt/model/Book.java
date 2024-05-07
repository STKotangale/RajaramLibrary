package com.raja.lib.invt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(name="bookNameABCDD")
    private String bookName;

    private char isBlock;

    @ManyToOne
    @JoinColumn(name = "author_idF", referencedColumnName = "authorId")
    private BookAuthor author;

    @ManyToOne
    @JoinColumn(name = "publication_idF", referencedColumnName = "publicationId")
    private BookPublication publication;

   
}