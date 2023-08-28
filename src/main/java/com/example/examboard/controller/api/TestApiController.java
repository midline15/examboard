package com.example.examboard.controller.api;

import com.example.examboard.dto.TestForm;
import com.example.examboard.entity.TestEntity;
import com.example.examboard.service.TestApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
//@RestController
public class TestApiController {

    private final TestApiService testApiService;

    @GetMapping("/api/lists")
    public ResponseEntity<List<TestEntity>> getList() {
        return new ResponseEntity<>(testApiService.viewList(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/api/lists2")
    public ResponseEntity<List<TestEntity>> getList2() {
        return ResponseEntity.ok(testApiService.viewList());
    }

    @GetMapping("/api/lists1")
    public @ResponseBody List<TestEntity> getList1() {
        return testApiService.viewList();
    }

    @GetMapping("/api/lists/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return testApiService.getOne(id);
    }

    @PostMapping("/api/entity")
    public @ResponseBody TestEntity insert(@RequestBody TestForm dto) {

        return testApiService.insert(dto);
    }

    @DeleteMapping("/api/lists/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return testApiService.delete(id);
    }

    @PatchMapping("/api/lists/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TestForm dto) {
        if (id != dto.getId()) {
            return ResponseEntity.badRequest().body("???");
        }
        return testApiService.update(dto);
    }
}
