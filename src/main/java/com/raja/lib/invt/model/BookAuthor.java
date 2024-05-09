package com.raja.lib.invt.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "invt_book_author")
public class BookAuthor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authorId")
	private int authorId;

	@NotBlank
	@Size(max = 100)
	@Column(name = "authorName")
	private String authorName;

	@Column(name = "authorAddress")
	private String authorAddress;

	@Column(name = "authorContactNo1")
	private String authorContactNo1;

	@Column(name = "authorContactNo2")
	private String authorContactNo2;

	@NotBlank
	@Size(max = 100)
	@Column(name = "authorEmailId")
	private String authorEmailId;

    @Column(name = "isblock", columnDefinition = "char(1) default 'N'")
	private char isblock;

}
