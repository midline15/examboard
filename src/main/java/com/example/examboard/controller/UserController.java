package com.example.examboard.controller;

import com.example.examboard.dto.UserCreateForm;
import com.example.examboard.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("user")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @Value("${logout_redirect_uri}")
    private String logoutUri;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;


    @GetMapping("/signup")
    public String signup(@ModelAttribute("dto") UserCreateForm dto) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("dto") UserCreateForm dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!dto.getPassword1().equals(dto.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect", "패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.createUser(dto);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/articles/lists";
    }

    @GetMapping("login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/logout/kakao")
    public String kakao(HttpSession session) {
        session.invalidate();

        return "redirect:https://kauth.kakao.com/oauth/logout?client_id="+clientId+"&logout_redirect_uri="+logoutUri;
    }
}
