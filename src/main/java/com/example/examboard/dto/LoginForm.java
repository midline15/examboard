package com.example.examboard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm {

    @NotBlank(message = "id를 입력하세요")
    private String userId;

    @NotBlank(message = "password 를 입력하세요")
    private String userPassword;
}
