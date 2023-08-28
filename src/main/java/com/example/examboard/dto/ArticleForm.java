package com.example.examboard.dto;

import com.example.examboard.entity.Article;
import com.example.examboard.entity.ArticleComment;
import com.example.examboard.entity.UserAccount;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleForm {

    private Long articleId;

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    private UserAccount user;

    private List<ArticleComment> commentList;

    private LocalDateTime createdAt;
    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();

    }
}
