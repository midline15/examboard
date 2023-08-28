package com.example.examboard.config.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String username;
    private String email;
    private OAuthType oauth;
    private String password;

    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver(attributes);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao(attributes);
        }
        return ofGoogle(attributes);
    }

    private static OAuthAttributes ofGoogle(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .username(attributes.get("email")+ "_google")
                .email((String) attributes.get("email"))
                .password("google123")
                .oauth(OAuthType.GOOGLE)
                .attributes(attributes)
                .build();
    }

    private static OAuthAttributes ofNaver (Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .username(response.get("email")+"_naver")
                .email((String) response.get("email"))
                .password("naver123")
                .oauth(OAuthType.NAVER)
                .attributes(response)
                .build();
    }
    private static OAuthAttributes ofKakao (Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");

        return OAuthAttributes.builder()
                .username(response.get("email")+"_kakao")
                .email((String) response.get("email"))
                .password("kakao123")
                .oauth(OAuthType.KAKAO)
                .attributes(response)
                .build();
    }
}
