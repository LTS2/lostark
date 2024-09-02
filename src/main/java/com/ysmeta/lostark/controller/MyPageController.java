package com.ysmeta.lostark.controller;

import com.sun.tools.jconsole.JConsoleContext;
import com.ysmeta.lostark.entity.CharacterEntity;
import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.service.CharacterService;
import com.ysmeta.lostark.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private UserService userService;

    private UserEntity user;

    @GetMapping("/info")
    public String info(HttpSession session,
                       Model model) {
        user = (UserEntity) session.getAttribute("user");
        model.addAttribute("user", user);
        return "/my-page/info";
    }

    @GetMapping("/activity")
    public String myPage(HttpSession session,
                         Model model) {
        model.addAttribute("user", user);
        return "/my-page/activity";
    }

    @GetMapping("/guestbook")
    public String guestbook(HttpSession session,
                            Model model) {
        model.addAttribute("user", user);
        return "/my-page/guestbook";
    }

    @PostMapping("/profile/upload")
    public String uploadProfileImage(@RequestParam("profileImage") MultipartFile file,
                                     RedirectAttributes redirectAttributes,
                                     HttpSession session,
                                     Model model) {
        user = (UserEntity) session.getAttribute("user");
        Long userId = user.getId();

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "파일을 선택해주세요.");
            return "redirect:/mypage/info";
        }
        try {
            userService.updateUserProfileImage(userId, file);
            UserEntity updatedUser = userService.findById(userId);
            session.setAttribute("user", updatedUser);
            redirectAttributes.addFlashAttribute("message", "성공적으로 변경되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/mypage/info";
    }
 }