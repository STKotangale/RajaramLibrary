package com.raja.lib.invt.resposne;

import java.io.Serializable;

import lombok.Data;

@Data
public class BookResponseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int bookId;
    private String bookName;
    

}
