package com.ysmeta.lostark.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home Controller
 *
 * @author : minjooo
 * @fileName : HomeControoler
 * @since : 2024/08/22
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String home (HttpSession session, Model model) {
        boolean loggedIn = session.getAttribute("user") != null;
        model.addAttribute("loggedIn", loggedIn);
        return "/main";
    }

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/register")
    public String register(){
        return "/register";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/";
    }

}