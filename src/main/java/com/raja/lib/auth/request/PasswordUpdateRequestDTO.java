package com.raja.lib.auth.request;

import lombok.Data;

@Data
public class PasswordUpdateRequestDTO {
    private int userId;
    private String password;
    private String confirmPassword;
}