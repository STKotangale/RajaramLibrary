package com.raja.lib.invt.model;

import java.io.Serializable;
import org.springframework.stereotype.Component;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Table(name = "invt_book_language")
public class BookLanguage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookLangId;

    @NotBlank
    @Size(max = 45)
    private String bookLangName;

    @Column(columnDefinition = "char(1) default 'N'")
    private Boolean isBlock;

}