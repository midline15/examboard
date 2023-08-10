package com.example.examboard.repository;

import com.example.examboard.entity.Article;
import com.example.examboard.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByTitleContains(String keyword, Pageable pageable);

    Page<Article> findByContentContains(String keyword, Pageable pageable);

    Page<Article> findByUser_UserIdContains(String keyword, Pageable pageable);

    Page<Article> findByUser_NicknameContains(String keyword, Pageable pageable);
}