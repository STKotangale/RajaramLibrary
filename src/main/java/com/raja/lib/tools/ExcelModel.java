package com.raja.lib.tools;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "aa_data_entry") 
@Data
public class ExcelModel {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SRNO")
    private int srNo;

    @Column(name = "ACCESSION_NO")
    private String accessionNo;

    @Column(name = "DATE_OF_ACCESSIONED")
    private String dateOfAccessioned;

    @Column(name = "BOOKS_NAME")
    private String booksName;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "AUTHOR_NAME")
    private String autherName;

    @Column(name = "PUBLISHER_NAME")
    private String publisheName;

    @Column(name = "YEAR_OF_PUBLICATION")
    private String yearOfPublication;

    @Column(name = "EDITION")
    private String edition;

    @Column(name = "PAGES")
    private String pages;

    @Column(name = "BOOKS_PRICE")
    private String booksprice;

    @Column(name = "NO_OF_COPIES")
    private String noOfCopies;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "KADAMBARI_NO")
    private String kadambarino;

}
