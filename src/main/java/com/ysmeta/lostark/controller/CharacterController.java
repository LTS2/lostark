package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.dto.CharacterDTO;
import com.ysmeta.lostark.entity.CharacterEntity;
import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.service.CharacterService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

/**
 * @author : ejum
 * @fileName : CharacterContoller
 * @since : 8/25/24
 */
@Controller
@Slf4j
@RequestMapping("/api/user/lostArk")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping("/regist")
    public String registerCharacter(@RequestParam String username, Model model) {
        CharacterDTO characterDTO = characterService.getCharacterByUsername(username);

        if (characterDTO != null) {
            model.addAttribute("character", characterDTO);
            log.info("Character found: {}", characterDTO);
        } else {
            model.addAttribute("character", null);
            model.addAttribute("errorMessage", "캐릭터를 찾을 수 없습니다."); // 에러 메시지 추가
            log.info("No characters found for username: {}", username);
        }

        return "my-page/regist";
    }

    @GetMapping("/regist")
    public String regist(HttpSession session, Model model) {
        UserEntity loggedInUser = (UserEntity) session.getAttribute("user");
        if (loggedInUser == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        List<CharacterEntity> characters = characterService.getCharactersByUserId(loggedInUser.getId());
        model.addAttribute("characters", characters);
        return "my-page/regist";
    }

    @PostMapping("/confirm")
    public String confirmCharacter(@RequestParam String characterName, HttpSession session, Model model) throws IOException {
        UserEntity loggedInUser = (UserEntity) session.getAttribute("user");

        // 캐릭터 조회
        List<CharacterEntity> characters = characterService.findCharacterByUsername(characterName);

        // 조회한 캐릭터를 유저와 연관지어 저장
        if (!characters.isEmpty()) {
            for (CharacterEntity character : characters) {
                characterService.saveCharacterForUser(character, loggedInUser);
            }
            // 세션에 캐릭터 정보 저장
            session.setAttribute("characters", characters);
        }

        model.addAttribute("characters", characters);
        return "my-page/regist";
    }

    @PostMapping("/delete")
    public String deleteCharacter(@RequestParam Long characterId, HttpSession session, RedirectAttributes redirectAttributes) {
        UserEntity loggedInUser = (UserEntity) session.getAttribute("user");
        if (loggedInUser != null) {
            characterService.deleteCharacter(characterId);
            log.info("Character with ID {} deleted successfully", characterId);
            redirectAttributes.addFlashAttribute("successMessage", "캐릭터가 성공적으로 삭제되었습니다.");
        }
        return "redirect:/api/user/lostArk/regist";
    }
}