package com.example.examboard.entity;

import com.example.examboard.dto.ArticleCommentDto;
import com.example.examboard.dto.ArticleForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ArticleComment extends AuditingFields{
    //article_id, user_id, content, created_at, modified_at, created_by, modified_by

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;

    @Column(length = 10000)
    private String content;

    public ArticleCommentDto of() {
        return ArticleCommentDto.builder()
                .commentId(commentId)
                .content(content)
                .build();
    }


    public ArticleComment update(ArticleCommentDto dto) {
        content = dto.getContent();
        return this;
    }
}
