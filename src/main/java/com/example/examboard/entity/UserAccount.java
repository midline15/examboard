package com.example.examboard.entity;

import com.example.examboard.config.oauth.OAuthType;
import com.example.examboard.entity.constant.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserAccount extends AuditingFields{
    //user_id, user_password, nickname, email, created_at, created_by, modified_at, modified_by

    @Id
    private String userId;

    private String userPassword;

    private String nickname;

    private String email;

    @Setter
    private UserRole userRole;


    @Enumerated(EnumType.STRING)
    private OAuthType oauth;
}
