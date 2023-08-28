package com.example.examboard.service;

import com.example.examboard.dto.UserCreateForm;
import com.example.examboard.entity.UserAccount;
import com.example.examboard.entity.constant.UserRole;
import com.example.examboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserCreateForm dto) {
        dto.setPassword1(passwordEncoder.encode(dto.getPassword1()));
        UserAccount user = dto.toEntity();
        if ("ADMIN".equals(user.getUserId().toUpperCase())) {
            user.setUserRole(UserRole.ADMIN);
        } else {
            user.setUserRole(UserRole.USER);
        }
        userRepository.save(user);
    }
}
