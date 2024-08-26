package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.entity.CharacterEntity;
import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.service.CharacterService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * MyPage Controller
 *
 * @author : minjooo
 * @fileName : MyPageController
 * @since : 2024/08/22
 */

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private CharacterService characterService;

    @GetMapping("/activity")
    public String myPage() {
        return "/my-page/activity";
    }

    @GetMapping("/guestbook")
    public String guestbook() {
        return "/my-page/guestbook";
    }

    @GetMapping("/regist")
    public String regist(HttpSession session, Model model) {
        UserEntity loggedInUser = (UserEntity) session.getAttribute("user");
        if (loggedInUser == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        List<CharacterEntity> characters = characterService.getCharactersByUserId(loggedInUser.getId());
        model.addAttribute("characters", characters);
        return "/my-page/regist";
    }
 }