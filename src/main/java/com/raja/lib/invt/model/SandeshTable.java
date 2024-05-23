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
@Table(name = "sandeshtable")
public class SandeshTable implements Serializable{
	
	private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sandeshId")
    private int sandeshId;

    @NotBlank
    @Size(max = 45)
    @Column(name="sandeshName")
    private String sandeshName;

    @Column(name = "isBlock", columnDefinition = "char(1) default 'N'")
    private char isBlock;

}
