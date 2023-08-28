package com.example.examboard.entity;

import com.example.examboard.dto.ApiArticleDto;
import com.example.examboard.dto.ApiArticleListDto;
import com.example.examboard.dto.ArticleForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Article extends AuditingFields{
    //user_id, title, content, created_by, modified_by, created_at, modified_at

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;

    private String title;

    @Column(length = 10000)
    private String content;

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleComment> commentList;

    public ArticleForm of() {
        return ArticleForm.builder()
                .articleId(articleId)
                .title(title)
                .content(content)
                .user(user)
                .commentList(commentList)
                .createdAt(createdAt)
                .build();
    }

    public ApiArticleListDto ofApiList() {
        return ApiArticleListDto.builder()
                .articleId(articleId)
                .title(title).build();
    }

    public ApiArticleDto ofApi() {
        return ApiArticleDto.builder()
                .articleId(articleId)
                .title(title)
                .content(content)
                .build();
    }

    public Article update(ArticleForm dto) {
        title = dto.getTitle();
        content = dto.getContent();

        return this;
    }

    public Article updateApi(ApiArticleDto dto) {
        title = dto.getTitle();
        content = dto.getContent();

        return this;
    }
}