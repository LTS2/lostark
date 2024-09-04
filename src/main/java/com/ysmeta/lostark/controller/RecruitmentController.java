package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.entity.CharacterEntity;
import com.ysmeta.lostark.entity.RecruitmentEntity;
import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.service.RecruitmentService;
import com.ysmeta.lostark.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : ejum
 * @fileName : RecruitmentController
 * @since : 8/23/24
 */
@Slf4j
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
    public String recruitment(Model model) {
        // 현재 날짜를 기준으로 필터링
        LocalDate today = LocalDate.now();
        List<RecruitmentEntity> recruitments = recruitmentService.findAllByOrderByCreatedDateDesc();
        recruitments.removeIf(recruitment -> recruitment.getStartDate().isBefore(today));

        model.addAttribute("recruitments", recruitments);
        return "recruitment/recruitment";
    }

    @GetMapping("/create")
    public String showCreateForm(HttpSession session, Model model) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        UserEntity result = userService.findById(user.getId());

        // 캐릭터 목록 추가
        List<CharacterEntity> characters = result.getCharacters();
        model.addAttribute("characters", characters);
        log.info("characters Lv ==>> {}", characters.get(1).getCharacterLevel());

        // 목표 레벨 정보를 클라이언트에 전달
        Map<String, Integer> goalLevels = new HashMap<>();
        goalLevels.put("에기르 (하드1680)", 1680);
        goalLevels.put("에기르 (노말1660)", 1660);
        goalLevels.put("베히모스 (노말1640)", 1640);
        goalLevels.put("카멘 (하드1630)", 1630);
        goalLevels.put("카멘 (노말1610)", 1610);
        goalLevels.put("상아탑 (하드1620)", 1620);
        goalLevels.put("상아탑 (노말1600)", 1600);
        goalLevels.put("카양겔 (하드1580)", 1580);
        goalLevels.put("카양겔 (노말1540)", 1540);

        model.addAttribute("goalLevels", goalLevels);

        return "recruitment/createRecruitment";
    }

    @PostMapping("/create")
    public String createRecruitment(@ModelAttribute RecruitmentEntity recruitmentEntity, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");

        if (user == null) {
            throw new RuntimeException("User not found in session");
        }

        // 오늘 날짜 가져오기
        LocalDate today = LocalDate.now();

        // 선택된 날짜가 오늘 날짜 이전인지 확인
        if (recruitmentEntity.getStartDate().isBefore(today)) {
            throw new RuntimeException("You cannot select a past date.");
        }

        recruitmentEntity.setUser(user);
        recruitmentService.save(recruitmentEntity);

        return "redirect:/api/recruitment";
    }

    @GetMapping("/check-login")
    public ResponseEntity<Map<String, Object>> checkLogin(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        UserEntity user = (UserEntity) session.getAttribute("user");
        boolean isLoggedIn = (user != null);
        response.put("loggedIn", isLoggedIn);

        if (isLoggedIn) {
            UserEntity result = userService.findById(user.getId());
            boolean hasCharacter = result.getCharacters() != null && !result.getCharacters().isEmpty();
            response.put("hasCharacter", hasCharacter);
        } else {
            response.put("hasCharacter", false);
        }

        return ResponseEntity.ok(response);
    }
    // 모집글 상세 페이지
    @GetMapping("/details/{id}")
    public String getRecruitmentDetails(@PathVariable Long id, Model model) {
        Optional<RecruitmentEntity> recruitment = recruitmentService.findById(id);
        if (recruitment.isEmpty()) {
            return "redirect:/api/recruitment"; // 모집글이 없는 경우 목록 페이지로 리다이렉트
        }
        model.addAttribute("recruitment", recruitment.get());
        return "recruitment/recruitmentDetails"; // 상세 페이지로 이동
    }
}