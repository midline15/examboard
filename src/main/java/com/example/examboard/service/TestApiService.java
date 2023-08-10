package com.example.examboard.service;

import com.example.examboard.dto.TestForm;
import com.example.examboard.entity.TestEntity;
import com.example.examboard.repository.TestEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestApiService {

    private final TestEntityRepository testEntityRepository;

    @Transactional(readOnly = true)
    public List<TestEntity> viewList() {
        return testEntityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getOne(Long id) {
        TestEntity entity = testEntityRepository.findById(id).orElse(null);
        if (entity == null) {
            return ResponseEntity.badRequest().body("없");
        }
        return ResponseEntity.ok(entity);
    }

    @Transactional
    public TestEntity insert(TestForm dto) {
        TestEntity entity = TestEntity.builder().memo(dto.getMemo()).build();
        return testEntityRepository.save(entity);
    }

    @Transactional
    public ResponseEntity<?> delete(Long id) {
        if (!testEntityRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(id + " 없");
        }
        testEntityRepository.deleteById(id);
        return ResponseEntity.ok(id+" 삭제 완료");
    }

    @Transactional
    public ResponseEntity<?> update(TestForm dto) {
        TestEntity entity = testEntityRepository.findById(dto.getId()).orElse(null);
        if (entity == null) {
            return ResponseEntity.badRequest().body("없");
        }
        entity.update(dto);
        return ResponseEntity.ok(entity);
    }
}
