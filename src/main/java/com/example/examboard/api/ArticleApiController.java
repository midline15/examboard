/*
package com.example.examboard.api;

import com.example.examboard.dto.ArticleCommentDto;
import com.example.examboard.dto.ArticleForm;
import com.example.examboard.entity.Article;
import com.example.examboard.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/articles")
@Controller
public class ArticleApiController {

    private final ArticleService articleService;

    @GetMapping({"/", ""})
    public ResponseEntity<?> getArticleList() {
        return ResponseEntity.ok(articleService.getArticleList());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getArticle(@PathVariable Long id) {
        ArticleForm dto = articleService.getArticle(id);
        if (dto == null) {
            return ResponseEntity.badRequest().body("존재하지 않는 게시글");
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping({"/", ""})
    public ResponseEntity<?> insertArticle(@RequestBody ArticleForm dto) {
        return articleService.insertArticle(dto);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @RequestBody ArticleForm dto) {
        if (id != dto.getArticleId()) {
            return ResponseEntity.badRequest().body("id 이상");
        }
        Article article = articleService.updateArticle(dto);
        if (article == null) {
            return ResponseEntity.badRequest().body("존재하지 않는 게시글");
        }
        return ResponseEntity.ok(article.of());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        return articleService.deleteArticle(id);
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<?> getCommentList(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getCommentList(id));
    }

    @GetMapping("{id}/comments/{a-id}")
    public ResponseEntity<?> getComment(@PathVariable Long id, @PathVariable("a-id") Long commentId) {
        return articleService.getComment(id, commentId);
    }

    @PostMapping("{id}/comments")
    public ResponseEntity<?> insertComment(@PathVariable Long id, @RequestBody ArticleCommentDto dto) {
        return ResponseEntity.ok(articleService.addComment(id,dto).of());
    }

    @DeleteMapping("{id}/comments/{a-id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @PathVariable("a-id") Long commentId) {
        return articleService.deleteComment(id,commentId);
    }

    @PatchMapping("{id}/comments/{a-id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @PathVariable("a-id") Long commentId, @RequestBody ArticleCommentDto dto) {
        return articleService.updateComment(id,commentId,dto);
    }
}
*/
