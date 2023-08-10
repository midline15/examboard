package com.example.examboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ExamboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamboardApplication.class, args);
    }

}
