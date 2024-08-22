package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    /**
     *
     * @param username
     * @return ServerName, CharacterName, CharacterLevel, CharacterClassName, ItemAvgLevel, ItemMaxLevel
     */
    @GetMapping("/api/user/lostark")
    @ResponseBody
    public String lostark(@RequestParam String username) {
        String result = testService.findCharacterName(username);
        return result;
    }

    @PostMapping("/login")
    public String login(){
        return "";
    }



}
