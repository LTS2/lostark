package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.entity.CharacterEntity;
import com.ysmeta.lostark.entity.RecruitmentEntity;
import com.ysmeta.lostark.entity.RecruitmentTeamEntity;
import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.service.CharacterService;
import com.ysmeta.lostark.service.RecruitmentService;
import com.ysmeta.lostark.service.RecruitmentTeamService;
import com.ysmeta.lostark.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final RecruitmentTeamService recruitmentTeamService;
    private final CharacterService characterService;
    private final UserService userService;

    public RecruitmentController(RecruitmentService recruitmentService,  RecruitmentTeamService recruitmentTeamService, CharacterService characterService, UserService userService) {
        this.recruitmentService = recruitmentService;
        this.recruitmentTeamService = recruitmentTeamService;
        this.characterService = characterService;
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
        return "recruitment/createRecruitment";
    }

    // 캐릭터 선택 시, 캐릭터 정보를 저장
    @PostMapping("/create")
    public String createRecruitment(@ModelAttribute RecruitmentEntity recruitmentEntity,
                                    @RequestParam("character") Long characterId,
                                    HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");


        if (user == null) {
            throw new RuntimeException("User not found in session");
        }

        CharacterEntity character = characterService.findById(characterId);
        if (character == null) {
            throw new RuntimeException("Character not found");
        }

        recruitmentEntity.setCharacterEntity(character);
        recruitmentEntity.setUser(user);

        LocalDate today = LocalDate.now();
        if (recruitmentEntity.getStartDate().isBefore(today)) {
            throw new RuntimeException("You cannot select a past date.");
        }
        recruitmentEntity.setApplyCount(0);
        recruitmentEntity.setStatus("모집 중");
        recruitmentService.save(recruitmentEntity);
        recruitmentTeamService.saveRecruitmentCharacter(recruitmentEntity.getId(), String.valueOf(recruitmentEntity.getCharacterEntity().getId()), user);

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
    public String getRecruitmentDetails(@PathVariable Long id,
                                        HttpSession session,
                                        Model model) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        Optional<RecruitmentEntity> recruitment = recruitmentService.findById(id);
        if (user != null) {
            List<CharacterEntity> characterList = characterService.getCharactersByUserId(user.getId());
            model.addAttribute("characterList", characterList);
        }
        if (recruitment.isEmpty()) {
            return "redirect:/api/recruitment";
        }
        model.addAttribute("recruitment", recruitment.get());

        List<RecruitmentTeamEntity> recruitmentTeams = recruitmentTeamService.findAllRecruitmentTeams();
        Long recruitmentId = id;
        List<RecruitmentTeamEntity> rtTeams = recruitmentTeamService.findTeamsByRecruitmentId(recruitmentId);

        model.addAttribute("recruitmentTeams", recruitmentTeams);
        model.addAttribute("rtTeams", rtTeams);
        model.addAttribute("sessionUser", user);
        return "recruitment/recruitmentDetails";
    }

    /* 모집글 지원 */
    @PostMapping("/submit-character")
    public String applyCharacter(@RequestParam("selectedCharacter") String selectedCharacterId,
                                 @RequestParam("recruitmentId") Long recruitmentId,
                                 HttpSession session,
                                 Model model) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        try {
            recruitmentTeamService.saveRecruitmentCharacter(recruitmentId, selectedCharacterId, user);
            return "redirect:/api/recruitment/details/" + recruitmentId;
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "redirect:/api/recruitment/details/" + recruitmentId;
        }
    }

    /* 모집글 삭제 */
    @PostMapping("/delete/{id}")
    public String deleteRecruitment(@PathVariable Long id,
                                    HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        recruitmentService.deleteRecruitment(id);
        return "redirect:/api/recruitment";
    }

    /* 모집글 지원 취소 */
    @PostMapping("/cancel/{id}")
    public String cancleRecruitment(@PathVariable Long id,
                                    HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        Long userId = user.getId();
        recruitmentTeamService.cancelApplication(id, userId, session);
        return "redirect:/api/recruitment/details/" + id;
    }
}