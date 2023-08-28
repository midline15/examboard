package com.example.examboard.controller;

import com.example.examboard.dto.*;
import com.example.examboard.entity.Article;
import com.example.examboard.entity.UserAccount;
import com.example.examboard.service.ArticleService;
import com.example.examboard.service.PaginationService;
import com.example.examboard.service.Paginator;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class BoardController {

    private final ArticleService articleService;
//    private final EntityManager em;

    @GetMapping({"/", "", "lists"})
    public String getArticlePage(Model model,
                                 @PageableDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(required = false) String searchType,
                                 @RequestParam(required = false) String searchValue) {

        Page<Article> articleList = articleService.getArticleList(searchType, searchValue, pageable.withPage(page-1));

        int totalPage = articleList.getTotalPages();

        PaginationService paging = new PaginationService();

        model.addAttribute("list",articleList);
        model.addAttribute("bar", paging.getPaginationBarNumbers(page, totalPage));
        model.addAttribute("paging", paging);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);
        return "view";
    }

    @GetMapping("{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleService.getArticle(id));

        return "detail";
    }

    @GetMapping("create")
    public String createArticle(@ModelAttribute("dto") ArticleForm dto) {
        return "new";
    }

    @PostMapping("create")
    public String createArticle(@Valid @ModelAttribute("dto") ArticleForm dto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "new";
        }

        articleService.saveArticle(dto, principal.getName());
        return "redirect:/articles";
    }


    @GetMapping("{id}/update")
    public String updateArticle(@PathVariable Long id,Model model) {
        model.addAttribute("dto", articleService.getArticle(id));
        return "update";
    }

    @PostMapping("update")
    public String updateArticle(@Valid @ModelAttribute("dto") ArticleForm articleForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update";
        }

        articleService.updateArticle(articleForm);
        return "redirect:/articles/"+articleForm.getArticleId();
    }

    @GetMapping("{id}/delete")
    public String deleteArticle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        articleService.deleteArticle(id);
        redirectAttributes.addFlashAttribute("msg", "삭제완료");
        return "redirect:/articles";
    }

    @PostMapping("{id}/articleComment")
    public String saveComment(@PathVariable Long id, ArticleCommentDto dto, Principal principal) {
        articleService.addComment(id,dto, principal.getName());

        return "redirect:/articles/"+id;
    }

    @PostMapping("{id}/articleComments/{article-comment-id}/delete")
    public String deleteComment(@PathVariable Long id, @PathVariable("article-comment-id") Long commentId) {
        //articleService.deleteComment(id, commentId);
        articleService.deleteComment(commentId);

        return "redirect:/articles/"+id;
    }

    /*@GetMapping({"/", ""})
    public String getArticlePage(Model model, SearchForm dto, @RequestParam(defaultValue = "1") int page) {

        Paginator paginator = new Paginator(5,15, articleService.findCnt(dto));

        List<Article> articleList = articleService.search(dto,(page-1)*15,15);

        model.addAttribute("list",articleList);
        model.addAttribute("paginator", paginator.getFixedBlock(page));
        model.addAttribute("dto", dto);
        return "view1";
    }*/
/*
    @GetMapping("login")
    public String login(@ModelAttribute("dto") LoginForm dto) {
        return "login";
    }

    @PostMapping("login")
    public String login(Model model, @Valid @ModelAttribute("dto") LoginForm dto, BindingResult bindingResult, HttpSession session) {

        UserAccount user = em.find(UserAccount.class, dto.getUserId());
        if (user == null) {
            if (dto.getUserId() != "") {
                bindingResult.addError(new FieldError("dto", "userId", "ID가 존재하지 않습니다."));
                dto.setUserId("");
            }
        } else {
            if (!user.getUserPassword().equals(dto.getUserPassword())) {
                bindingResult.addError(new FieldError("dto", "userPassword", "비밀번호를 확인해주세요"));
                dto.setUserPassword("");
            }
        }
        if (bindingResult.hasErrors()) {
            return "login";
        }

        session.setAttribute("principal", user);
        model.addAttribute("message", "로그인 성공");

        return "redirect:/articles";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/articles";
    }

    @GetMapping("signup")
    public String signup(@ModelAttribute("dto") SignupForm dto) {
        return "signup";
    }

    @Transactional
    @PostMapping("signup")
    public String signup(@Valid @ModelAttribute("dto") SignupForm dto, BindingResult bindingResult,HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }
        UserAccount findUser = em.find(UserAccount.class, dto.getUserId());
        if (findUser != null) {
            bindingResult.addError(new FieldError("dto", "userId", "이미 존재하는 ID 입니다."));
            return "signup";
        }
        UserAccount signupUser = dto.toEntity();
        session.setAttribute("principal",signupUser);
        em.persist(signupUser);
        session.invalidate();

        return "redirect:/articles";
    }*/
}
