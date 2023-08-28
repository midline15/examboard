package com.example.examboard.config.oauth;

import com.example.examboard.config.auth.PrincipalDetails;
import com.example.examboard.entity.UserAccount;
import com.example.examboard.entity.constant.UserRole;
import com.example.examboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuth2UserDetailsServiceImpl extends DefaultOAuth2UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, oAuth2User.getAttributes());

        UserAccount user = save(attributes);

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

    private UserAccount save(OAuthAttributes attributes) {
        UserAccount user = userRepository.findById(attributes.getUsername())
                .orElse(UserAccount.builder()
                        .userId(attributes.getUsername())
                        .email(attributes.getEmail())
                        .userPassword(passwordEncoder.encode(attributes.getPassword()))
                        .oauth(attributes.getOauth())
                        .userRole(UserRole.USER)
                        .build()
                );
        return userRepository.save(user);
    }
}

