package com.example.examboard.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    /*@Autowired
    HttpSession session;*/

    @Bean
    public AuditorAware<String> auditorProvider() {
        /*return ()->{

            UserAccount user = (UserAccount) session.getAttribute("principal");
            if (user != null) {
                return Optional.of(user.getUserId());
            }
            return Optional.of("anonymous");
        };*/
        return new AuditorAwareImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
