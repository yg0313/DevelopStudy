package com.example.userservice.vo.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 사용자의 로그인 요청 데이터 값.
 */
@Data
public class RequestLogin {

    @NotNull(message = "email cannot be null")
    @Size(min = 2, message = "email not be less than two character")
    @Email
    private String email;

    @NotNull(message = "password cannot be null")
    @Size(min = 8, message = "password must be equals or greater than two character")
    private String password;
}
