package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.dto.RequestDTO;
import com.ysmeta.lostark.entity.GuestbookEntity;
import com.ysmeta.lostark.entity.RecruitmentEntity;
import com.ysmeta.lostark.entity.RecruitmentTeamEntity;
import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.service.RecruitmentService;
import com.ysmeta.lostark.service.RecruitmentTeamService;
import com.ysmeta.lostark.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * MyPage Controller
 *
 * @author : minjooo
 * @fileName : MyPageController
 * @since : 2024/08/22
 */

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    private RecruitmentTeamService recruitmentTeamService;

    private UserEntity user;

    @GetMapping("/info")
    public String info(HttpSession session,
                       Model model) {
        user = (UserEntity) session.getAttribute("user");
        model.addAttribute("user", user);
        return "/my-page/info";
    }

    @GetMapping("/activity")
    public String myPage(HttpSession session,
                         Model model) {
        List<RecruitmentEntity> recruitList = userService.getRecruitList(user);
        List<RecruitmentTeamEntity> rtList = userService.getRecruitTeamList(user);
        // 모집글 참여 리스트 중, 모집글 작성 리스트에 포함되지 않은 항목만 필터링
        if (rtList != null) {
            List<RecruitmentTeamEntity> filteredRtList = rtList.stream()
                    .filter(rt -> recruitList.stream()
                            .noneMatch(recruit -> recruit.getId().equals(rt.getRecruitmentEntity().getId())))
                    .collect(Collectors.toList());

            // 필터링된 모집글 참여 리스트를 모델에 추가
            model.addAttribute("rtList", filteredRtList);
        }

        model.addAttribute("user", user);
        model.addAttribute("recruitList", recruitList);
        return "/my-page/activity";
    }

    /*
    * 방명록 내용 가져오기
    * */
    @GetMapping("/guestbook")
    public String guestbook(HttpSession session,
                            Model model) {
        user = (UserEntity) session.getAttribute("user");
        List<GuestbookEntity> guestbookEntityList = userService.getGuestbookEntityList(user.getId());
        int totalGuestbooks = guestbookEntityList.size();
        model.addAttribute("guestbookList", guestbookEntityList);
        model.addAttribute("totalGuestbooks", totalGuestbooks);
        model.addAttribute("user", user);
        return "/my-page/guestbook";
    }

    /*
    * 회원 프로필 사진 변경
    * */
    @PostMapping("/profile/upload")
    public String uploadProfileImage(@RequestParam("profileImage") MultipartFile file,
                                     RedirectAttributes redirectAttributes,
                                     HttpSession session,
                                     Model model) {
        user = (UserEntity) session.getAttribute("user");
        Long userId = user.getId();

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "파일을 선택해주세요.");
            return "redirect:/mypage/info";
        }
        try {
            userService.updateUserProfileImage(userId, file);
            UserEntity updatedUser = userService.findById(userId);
            session.setAttribute("user", updatedUser);
            redirectAttributes.addFlashAttribute("message", "성공적으로 변경되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
        }
        return "redirect:/mypage/info";
    }

    /*
    * 방명록 작성
    * */
    @PostMapping("/guestbook")
    public ResponseEntity<?> addGuestbookEntry(@RequestBody RequestDTO requestDTO,
                                               HttpSession session) {
       try {
           Optional<RecruitmentEntity> recruitmentOpt = recruitmentService.findById(requestDTO.getRecruitmentId());
           String goal = recruitmentOpt.map(RecruitmentEntity::getGoal).orElse("모집글을 찾을 수 없습니다.");
           requestDTO.setGoal(goal);
           userService.saveGuestbook(requestDTO, session);
           return ResponseEntity.ok("방명록이 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("방명록 등록 중 오류가 발생했습니다.");
        }
    }

    /* 마이페이지 > 지원한 모집글 정보 */
    @GetMapping("/recruitment/details/{id}")
    public String getRecruitmentDetails(@PathVariable Long id,
                                        Model model,
                                        HttpSession session) {
        // 해당 모집글 정보
        Optional<RecruitmentEntity> recruitment = recruitmentService.findById(id);

        // 해당 모집글의 모든 참여자
        List<RecruitmentTeamEntity> applyUsers = recruitmentTeamService.findTeamsByRecruitmentId(id);
        model.addAttribute("applyUsers", applyUsers);
        model.addAttribute("recruitment", recruitment);

        user = (UserEntity) session.getAttribute("user");
        model.addAttribute("loginUser", user);
        model.addAttribute("user", user);
        return "/my-page/act";
    }

}