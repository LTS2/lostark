package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.entity.BoardEntity;
import com.ysmeta.lostark.entity.UserEntity;
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


    /* 커뮤니티 모든 게시글 view */
    @GetMapping
    public String community (Model model) {
        model.addAttribute("posts", boardService.getAllPosts());
        return "/community/community";
    }

    /* 커뮤니티 게시글 작성 페이지 view */
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
            return "redirect:/community";
        } else {
            model.addAttribute("error", "Failed to create post. Please try again.");
            return "community/create-post";
        }
    }

    /* 커뮤니티 게시글 상세 페이지 */
    @GetMapping("/view/{id}")
    public String viewPost(@PathVariable Long id,
                           HttpSession session,
                           Model model) {
        BoardEntity post = boardService.getPostById(id);
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (post == null) {
            return "redirect:/community";
        }
        model.addAttribute("postedUserId", post.getUser() != null ? post.getUser().getId() : null);
        model.addAttribute("user", user);
        model.addAttribute("post", post);
        return "community/view-post";
    }

    /* 커뮤니티 게시글 수정 페이지 view */
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id,
                               Model model,
                               HttpSession session) {
        BoardEntity post = boardService.getPostById(id);
        if (post == null) {
            return "redirect:/community";
        }

        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null || !user.getId().equals(post.getUser().getId())) {
            return "redirect:/community";
        }
        model.addAttribute("post", post);
        return "community/edit-post";
    }

    /* 커뮤니티 게시글 수정 */
    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam String content,
                             HttpSession session) {

        BoardEntity post = boardService.getPostById(id);

        if (post == null) {
            return "redirect:/community";
        }

        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null || !user.getId().equals(post.getUser().getId())) {
            return "redirect:/community";
        }
        post.setTitle(title);
        post.setContent(content);
        boardService.updatePost(post);

        return "redirect:/community/view/" + id;
    }
}