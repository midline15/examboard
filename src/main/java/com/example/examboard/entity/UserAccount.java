package com.example.examboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

}
