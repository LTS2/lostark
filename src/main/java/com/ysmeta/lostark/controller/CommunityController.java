package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 커뮤니티 Controller
 *
 * @author : minjooo
 * @fileName : CommunityController
 * @since : 2024/09/17
 */

@Slf4j
@Controller
@RequestMapping("/community")
public class CommunityController {

    private final BoardService boardService;

    public CommunityController(BoardService boardService) {
        this.boardService = boardService;
    }
    @GetMapping
    public String community (Model model) {
        model.addAttribute("posts", boardService.getAllPosts());
        log.info(">>>> posts :: " + boardService.getAllPosts());
        return "/community/community";
    }

    @GetMapping("/create")
    public String createPost() {
        return "/community/create-post";
    }

    /* 커뮤니티 게시글 작성 */
    @PostMapping("/create")
    public String submitPost(@RequestParam String title,
                             @RequestParam String content,
                             HttpSession session,
                             Model model) {
        boolean success = boardService.createPost(title, content, session);
        if (success) {
            log.info(">>>> 게시글 작성 성공");
            return "redirect:/community";
        } else {
            log.info(">>>> 게시글 작성 실패");
            model.addAttribute("error", "Failed to create post. Please try again.");
            return "community/create-post";
        }
    }
}