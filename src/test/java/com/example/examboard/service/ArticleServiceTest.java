package com.example.examboard.service;

import com.example.examboard.dto.SearchForm;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    ArticleService articleService;

    @Test
    @DisplayName("조회 쿼리 테스트")
    public void searchTest() {
        SearchForm dto = new SearchForm();
        dto.setSearchType("제목");
        dto.setSearchValue("Etiam");

        String query="select count(*) from Article a";
        if (dto.getSearchType().equals("제목"))
            query = "select count(*) from Article a where a.title like :keyword";
        if (dto.getSearchType().equals("본문"))
            query = "select count(*) from Article a where a.content like :keyword";
        if (dto.getSearchType().equals("id"))
            query = "select count(*) from Article a where a.user.userId like :keyword";
        if (dto.getSearchType().equals("닉네임"))
            query = "select count(*) from Article a where a.user.nickname like :keyword";

        System.out.println((((Number)em.createQuery(query)
                .setParameter("keyword", "%"+dto.getSearchValue()+"%")
                .getSingleResult()).longValue()));
    }
}