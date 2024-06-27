package com.raja.lib.invt.resposne;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionInfoDTO {
    private String sessionFromDt;
    private String sessionToDt;
    private String currentDate;
}
