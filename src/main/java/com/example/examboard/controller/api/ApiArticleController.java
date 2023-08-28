package com.example.examboard.controller.api;

import com.example.examboard.dto.ApiArticleDto;
import com.example.examboard.dto.ApiArticleListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/articles")
@RequiredArgsConstructor
@RestController
public class ApiArticleController {

    private final ApiArticleService apiArticleService;

    @GetMapping
    public List<ApiArticleListDto> getArticleList() {
        return apiArticleService.getArticleList();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getArticle(@PathVariable Long id) {
        return apiArticleService.getArticle(id);
    }

    @PostMapping
    public ResponseEntity<?> insertArticle(@RequestBody ApiArticleDto dto) {
        return apiArticleService.insertArticle(dto);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @RequestBody ApiArticleDto dto) {
        return apiArticleService.updateArticle(id, dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        return apiArticleService.deleteArticle(id);
    }
}