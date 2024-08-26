package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.entity.RecruitmentEntity;
import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.service.RecruitmentService;
import com.ysmeta.lostark.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : ejum
 * @fileName : RecruitmentController
 * @since : 8/23/24
 */
@Controller
@RequestMapping("/api/recruitment")
public class RecruitmentController {
    private final RecruitmentService recruitmentService;

    private final UserService userService;

    public RecruitmentController(RecruitmentService recruitmentService, UserService userService) {
        this.recruitmentService = recruitmentService;
        this.userService = userService;
    }

    @GetMapping
    public String recruitment(Model model){
        List<RecruitmentEntity> recruitments = recruitmentService.findAll();
        model.addAttribute("recruitments", recruitments);
        return "recruitment/recruitment";
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "recruitment/createRecruitment"; // 모집글 작성 폼 페이지로 이동
    }

    @PostMapping("/create")
    public String createRecruitment(@ModelAttribute RecruitmentEntity recruitmentEntity, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");

        if (user == null) {
            throw new RuntimeException("User not found in session");
        }

        recruitmentEntity.setUser(user);

        recruitmentService.save(recruitmentEntity);

        return "redirect:/api/recruitment";
    }

    @GetMapping("/check-login")
    public ResponseEntity<Map<String, Boolean>> checkLogin(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        boolean isLoggedIn = (user != null);
        System.out.println("User in session: " + user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("loggedIn", isLoggedIn);
        return ResponseEntity.ok(response);
    }
}
