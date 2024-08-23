package com.ysmeta.lostark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Please explain the class!!
 *
 * @author : minjooo
 * @fileName : ChattingController
 * @since : 2024/08/23
 */

@Controller
@RequestMapping("/chatting")
public class ChattingController {

    @GetMapping
    public String chatting() {
        return "/chatting/chatting";
    }
}