package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.dto.RequestDTO;
import com.ysmeta.lostark.entity.GuestbookEntity;
import com.ysmeta.lostark.entity.RecruitmentEntity;
import com.ysmeta.lostark.entity.RecruitmentTeamEntity;
import com.ysmeta.lostark.entity.UserEntity;
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
        if (rtList != null) {
            System.out.println("지원한 리스트 :: " + rtList);
            model.addAttribute("rtList", rtList);
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
           userService.saveGuestbook(requestDTO, session);
           return ResponseEntity.ok("방명록이 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("방명록 등록 중 오류가 발생했습니다.");
        }
    }
 }