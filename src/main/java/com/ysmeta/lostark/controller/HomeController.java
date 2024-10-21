package com.ysmeta.lostark.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @ModelAttribute("loggedIn")
    public boolean addLoggedInAttribute(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    @RequestMapping("/")
    public String home (HttpSession session, Model model) {
        boolean loggedIn = session.getAttribute("user") != null;
        model.addAttribute("loggedIn", loggedIn);
        return "/main";
    }

    @GetMapping("/login")
    public String login(){
        return "/auth/login";
    }

    @GetMapping("/register")
    public String register(){
        return "/auth/register";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/";
    }

}