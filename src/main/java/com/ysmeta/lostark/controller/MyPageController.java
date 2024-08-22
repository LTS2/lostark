package com.ysmeta.lostark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/activity")
    public String myPage() {
        return "/my-page/activity";
    }

    @GetMapping("/guestbook")
    public String guestbook() {
        return "/my-page/guestbook";
    }

 }