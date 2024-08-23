package com.ysmeta.lostark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @author : ejum
 * @fileName : RecruitmentController
 * @since : 8/23/24
 */
@Controller
@RequestMapping("/api/recruitment")
public class RecruitmentController {

    @GetMapping
    public String recruitment(){
        return "recruitment/recruitment";
    }

    @GetMapping("zz")
    public String test(SessionAttribute sessionAttribute){
        return "";
    }



}
