package com.ysmeta.lostark.controller;

import org.springframework.stereotype.Controller;
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
    public String home () {
        return "/main";
    }
}