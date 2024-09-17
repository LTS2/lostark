package com.ysmeta.lostark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 커뮤니티 Controller
 *
 * @author : minjooo
 * @fileName : CommunityController
 * @since : 2024/09/17
 */

@Controller
@RequestMapping("/community")
public class CommunityController {

    @GetMapping
    public String community() {
        return "/community/community";
    }
}