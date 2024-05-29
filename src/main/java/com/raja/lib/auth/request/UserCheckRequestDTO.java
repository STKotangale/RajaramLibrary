package com.raja.lib.auth.request;

import lombok.Data;

@Data
public class UserCheckRequestDTO {
    private String email;
    private long mobileNo;
}
