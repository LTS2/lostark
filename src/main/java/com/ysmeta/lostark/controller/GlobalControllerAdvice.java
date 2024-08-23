package com.ysmeta.lostark.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("loggedIn")
    public boolean addLoggedInAttribute(HttpSession session) {
        return session.getAttribute("user") != null;
    }
}
