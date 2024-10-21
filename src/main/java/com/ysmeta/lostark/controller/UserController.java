package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String save(@ModelAttribute UserEntity user) {
        userService.saveUser(user);
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리다이렉트
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserEntity user, HttpSession session, Model model) {
        boolean isValidUser = userService.login(user.getUsername(), user.getPassword())
                .map(loggedInUser -> {
                    session.setAttribute("user", loggedInUser); // 로그인된 사용자 정보를 세션에 저장
                    return true;
                })
                .orElse(false);

        if (isValidUser) {
            return "redirect:/"; // 로그인 후 홈 페이지로 리다이렉트
        } else {
            model.addAttribute("loginError", true); // 로그인 실패 시 에러 플래그 설정
            return "/auth/login"; // 로그인 페이지로 리턴
        }
    }


    @GetMapping("/check-username")
    public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestParam String username) {
        boolean available = !userService.existsByUsername(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", available);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-login")
    public ResponseEntity<Map<String, Boolean>> checkLogin(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        boolean isLoggedIn = (user != null);
        Map<String, Boolean> response = new HashMap<>();
        response.put("loggedIn", isLoggedIn);
        return ResponseEntity.ok(response);
    }
}