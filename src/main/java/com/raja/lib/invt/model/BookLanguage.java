package com.raja.lib.invt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book_language")
public class BookLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookLangId;

    private String bookLangName;

    private Boolean isBlock;

    public BookLanguage(String bookLangName, Boolean isBlock) {
        this.bookLangName = bookLangName;
        this.isBlock = isBlock;
    }
}
