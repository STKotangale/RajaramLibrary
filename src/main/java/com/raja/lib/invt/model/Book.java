package com.raja.lib.invt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    private String bookName;

    private String isBlock;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "authorId")
    private BookAuthor author;

    @ManyToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "publicationId")
    private BookPublication publication;

    public Book(String bookName, String isBlock, BookAuthor author, BookPublication publication) {
        this.bookName = bookName;
        this.isBlock = isBlock;
        this.author = author;
        this.publication = publication;
    }
}