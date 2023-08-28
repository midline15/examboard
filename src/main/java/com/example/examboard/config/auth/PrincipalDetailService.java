package com.example.examboard.config.auth;

import com.example.examboard.entity.UserAccount;
import com.example.examboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findById(username).orElseThrow(() -> {
            return new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        });

        return new PrincipalDetails(user);
    }
}
