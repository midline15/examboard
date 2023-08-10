package com.example.examboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleCommentDto {

    private Long commentId;

    private String content;
}
