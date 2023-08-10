package com.example.examboard.dto;

import com.example.examboard.entity.UserAccount;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupForm {

    @NotBlank(message = "id를 입력하세요")
    private String userId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 4,max = 12)
    private String userPassword;

    @NotBlank(message = "이메일을 입력하세요")
    @Email(message = "이메일 형식을 확인하세요")
    private String email;

    @NotBlank(message = "닉네임을 입력하세요")
    private String nickname;

    public UserAccount toEntity() {
        return UserAccount.builder()
                .userId(this.userId)
                .userPassword(this.userPassword)
                .email(this.email)
                .nickname(this.nickname)
                .build();
    }
}
