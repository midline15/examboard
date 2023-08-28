package com.example.examboard.controller.api;

import com.example.examboard.dto.ApiArticleDto;
import com.example.examboard.dto.ApiArticleListDto;
import com.example.examboard.entity.Article;
import com.example.examboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApiArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public List<ApiArticleListDto> getArticleList() {
        return articleRepository.findAll().stream().map(Article::ofApiList).toList();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getArticle(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.badRequest().body(id + "번 게시글이 존재하지 않습니다");
        }
        return ResponseEntity.ok(article.ofApi());
    }

    @Transactional
    public ResponseEntity<?> insertArticle(ApiArticleDto dto) {
        return ResponseEntity.ok(articleRepository.save(dto.toEntity()).ofApi());
    }

    @Transactional
    public ResponseEntity<?> updateArticle(Long id, ApiArticleDto dto) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.badRequest().body(id + "번 게시글이 존재하지 않습니다.");
        }
        Article updatedArticle = articleRepository.save(article.updateApi(dto));
        return ResponseEntity.ok(updatedArticle.ofApi());
    }


    @Transactional
    public ResponseEntity<?> deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.badRequest().body(id + "번 게시글이 존재하지 않습니다.");
        }
        articleRepository.delete(article);
        return ResponseEntity.ok(id + "번 게시글을 삭제했습니다.");
    }
}