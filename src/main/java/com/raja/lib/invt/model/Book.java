package com.raja.lib.invt.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
@Entity
@Table(name = "invt_book")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookId")
    private int bookId;

    @NotBlank
    @Column(name="bookName")
    private String bookName;
    
    @ManyToOne
    @JoinColumn(name = "bookTypeIdF", referencedColumnName = "bookTypeId")
    private BookType bookTypeIdF;
    
    @ManyToOne
    @JoinColumn(name = "bookLangIdF", referencedColumnName = "bookLangId")
    private BookLanguage bookLangIdF;
    
    @ManyToOne
    @JoinColumn(name = "publicationIdF", referencedColumnName = "publicationId")
    private BookPublication publicationIdF;


    @ManyToOne
    @JoinColumn(name = "authorIdF", referencedColumnName = "authorId")
    private BookAuthor authorIdF;

    @Column(name = "isBlock", columnDefinition = "char(1) default 'N'")
    private char isBlock;

        
}