package com.example.examboard.entity;

import com.example.examboard.repository.TestEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestEntityTest {

    @Autowired
    TestEntityRepository testEntityRepository;

    @Test
    void testDataInsert() {
        for (int i = 0; i < 10; i++) {
            testEntityRepository.save(TestEntity.builder()
                    .memo("테스트"+i).build());
        }
    }

    @Test
    void testList() {
        testEntityRepository.findAll().forEach(test -> System.out.println(test.getMemo()));
    }
}