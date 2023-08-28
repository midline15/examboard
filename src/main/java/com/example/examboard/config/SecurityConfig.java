package com.example.examboard.config;

import com.example.examboard.config.oauth.OAuth2UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuth2UserDetailsServiceImpl oAuth2UserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/css/**", "/js/**", "/image/**").permitAll()
                        .requestMatchers("/", "/articles", "/articles/lists", "/user/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/articles/lists", true)
                        .usernameParameter("userId")
                        .passwordParameter("userPassword"))
                .logout(out -> out
                        .logoutSuccessUrl("/articles/lists")
                        .logoutUrl("/logout"))
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/articles", true)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserDetailsService)))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

}
