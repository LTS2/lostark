package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author : ejum
 * @fileName : UserContoller
 * @since : 8/23/24
 */
@Controller
@RequestMapping("/api/user")
public class UserContoller {

    private final UserService userService;

    public UserContoller(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String save(@ModelAttribute UserEntity user) {

            userService.saveUser(user);
            return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserEntity user, HttpSession session) {
        return userService.login(user.getEmail(), user.getPassword())
                .map(loggedInUser -> {
                    session.setAttribute("user", loggedInUser);
                    return "/main";
                })
                .orElse("Invalid email or password");
    }


//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "Logged out successfully";
//    }

}
