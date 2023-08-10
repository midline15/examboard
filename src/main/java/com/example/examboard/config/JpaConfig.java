package com.example.examboard.config;

import com.example.examboard.entity.UserAccount;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

    /*@Autowired
    HttpSession session;*/

    @Bean
    public AuditorAware<String> auditorProvider(HttpSession session) {
        return ()->{

            UserAccount user = (UserAccount) session.getAttribute("principal");
            if (user != null) {
                return Optional.of(user.getUserId());
            }
            return Optional.of("anonymous");
        };
    }
}
