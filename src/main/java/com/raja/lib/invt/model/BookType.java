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
@Table(name="book_type")
public class BookType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userMemberId;
    
    private String bookTypeName;
    
    private String isBlock;

    public BookType(String bookTypeName, String isBlock) {
        this.bookTypeName = bookTypeName;
        this.isBlock = isBlock;
    }
}
