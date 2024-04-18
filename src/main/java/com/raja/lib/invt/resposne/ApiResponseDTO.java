package com.raja.lib.invt.resposne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO<T> {
	private Boolean success;
	private String message;
	private T data;
	private int statusCode;
}
