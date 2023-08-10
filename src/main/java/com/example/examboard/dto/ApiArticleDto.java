package com.example.examboard.dto;

import com.example.examboard.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiArticleDto {

    private Long articleId;

    private String title;

    private String content;

    public Article toEntity() {
        return Article.builder()
                .articleId(articleId)
                .title(title)
                .content(content)
                .build();
    }
}