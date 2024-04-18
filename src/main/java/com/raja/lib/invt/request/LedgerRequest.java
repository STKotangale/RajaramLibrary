package com.raja.lib.invt.request;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Component
public class LedgerRequest {

	private String ledgerCode;
	private String ledgerName;;

}
