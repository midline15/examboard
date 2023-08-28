package com.example.examboard.service;

import com.example.examboard.dto.ArticleCommentDto;
import com.example.examboard.dto.ArticleForm;
import com.example.examboard.dto.SearchForm;
import com.example.examboard.entity.Article;
import com.example.examboard.entity.ArticleComment;
import com.example.examboard.entity.UserAccount;
import com.example.examboard.repository.ArticleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public long findCnt(SearchForm dto) {
        if (dto.getSearchType() == "" || dto.getSearchType() == null) {
            return ((Number) em.createQuery("select count(*) from Article")
                    .getSingleResult()).intValue();
        }

        String query = "";
        if (dto.getSearchType().equals("제목"))
            query = "select count(*) from Article a where a.title like :keyword";
        if (dto.getSearchType().equals("본문"))
            query = "select count(*) from Article a where a.content like :keyword";
        if (dto.getSearchType().equals("id"))
            query = "select count(*) from Article a where a.user.userId like :keyword";
        if (dto.getSearchType().equals("닉네임"))
            query = "select count(*) from Article a where a.user.nickname like :keyword";

        return ((Number) em.createQuery(query)
                .setParameter("keyword", "%" + dto.getSearchValue() + "%")
                .getSingleResult()).longValue();
    }

    @Transactional
    public List<Article> search(SearchForm dto, int startIndex, int pageSize) {
        String sort = " order by a.articleId desc";
        if (dto.getSearchType() == "" || dto.getSearchType() == null) {
            return em.createQuery("select a from Article a" + sort, Article.class)
                    .setFirstResult(startIndex)
                    .setMaxResults(pageSize)
                    .getResultList();
        }

        String query = "";
        if (dto.getSearchType().equals("제목"))
            query = "select a from Article a where a.title like :keyword";
        if (dto.getSearchType().equals("본문"))
            query = "select a from Article a where a.content like :keyword";
        if (dto.getSearchType().equals("id"))
            query = "select a from Article a where a.user.userId like :keyword";
        if (dto.getSearchType().equals("닉네임"))
            query = "select a from Article a where a.user.nickname like :keyword";

        return em.createQuery(query + sort, Article.class)
                .setParameter("keyword", "%" + dto.getSearchValue() + "%")
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Page<Article> getArticleList(String searchType, String searchValue, Pageable pageable) {
        if (searchType != null) {
            switch (searchType) {
                case "제목":
                    return articleRepository.findByTitleContains(searchValue,pageable);
                case "본문":
                    return articleRepository.findByContentContains(searchValue,pageable);
                case "id":
                    return articleRepository.findByUser_UserIdContains(searchValue,pageable);
                case "닉네임":
                    return articleRepository.findByUser_NicknameContains(searchValue,pageable);
            }
        }

        return articleRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public ArticleForm getArticle(Long id) {
        return articleRepository.findById(id).get().of();
    }

    @Transactional
    public ResponseEntity<?> deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("존재하지 않는 게시글");
        }
        articleRepository.deleteById(id);
        return ResponseEntity.ok(id + " 삭제 완료");
    }

    @Transactional
    public Article updateArticle(ArticleForm dto) {
        Article article = articleRepository.findById(dto.getArticleId()).orElse(null);
        if (article == null) {
            return article;
        }
        return article.update(dto);
    }

    @Transactional
    public ArticleComment addComment(Long id, ArticleCommentDto dto, String userId) {
        Article article = articleRepository.findById(id).get();

        ArticleComment comment = ArticleComment.builder()
                .article(article)
                .content(dto.getContent())
                .user(em.find(UserAccount.class, userId))
                .build();

        article.getCommentList().add(comment);

        return comment;
    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long id, Long commentId) {
        ArticleComment comment = em.find(ArticleComment.class, commentId);
        ResponseEntity resp = commentCheck(id, comment);
        if (resp != null) {
            return resp;
        }
        em.remove(comment);
        return ResponseEntity.ok(commentId + " 삭제 완료");
    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long commentId) {
        ArticleComment comment = em.find(ArticleComment.class, commentId);
        if (comment == null) {
            return ResponseEntity.badRequest().body("없");
        }

        em.remove(comment);
        return ResponseEntity.ok(commentId + " 삭제 완료");
    }

    @Transactional
    public void saveArticle(ArticleForm dto, String username) {
        UserAccount user = em.find(UserAccount.class, username);
        Article article = Article.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();
        articleRepository.save(article);
    }

    @Transactional
    public ResponseEntity<?> insertArticle(ArticleForm dto) {
        return ResponseEntity.ok(articleRepository.save(dto.toEntity()));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getCommentList(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.badRequest().body("없");
        }
        return ResponseEntity.ok(article.getCommentList().stream().map(ArticleComment::of).toList());

    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getComment(Long id, Long commentId) {
        ArticleComment comment = em.find(ArticleComment.class, commentId);
        ResponseEntity resp = commentCheck(id, comment);

        if (resp != null) {
            return resp;
        }
        return ResponseEntity.ok(comment.of());
    }

    @Transactional
    public ResponseEntity<?> updateComment(Long id, Long commentId, ArticleCommentDto dto) {
        if (!commentId.equals(dto.getCommentId())) {
            return ResponseEntity.badRequest().body("commentId 이상");
        }
        ArticleComment comment = em.find(ArticleComment.class, commentId);
        ResponseEntity resp = commentCheck(id, comment);

        if (resp != null) {
            return resp;
        }
        return ResponseEntity.ok(comment.update(dto).of());
    }

    private ResponseEntity<?> commentCheck(Long id, ArticleComment comment) {
        if (comment == null) {
            return ResponseEntity.badRequest().body("존재하지 않는 댓글");
        }

        if (comment.getArticle().getArticleId() != id) {
            return ResponseEntity.badRequest().body("현재 게시글에 없는 댓글입니다.");
        }
        return null;
    }
}
