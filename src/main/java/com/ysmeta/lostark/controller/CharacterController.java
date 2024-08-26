package com.ysmeta.lostark.controller;

import com.ysmeta.lostark.dto.CharacterDTO;
import com.ysmeta.lostark.entity.CharacterEntity;
import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.service.CharacterService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/register")
    public String registerCharacter(@RequestParam String username, Model model) {
        try {
            List<CharacterEntity> characters = characterService.findCharacterByUsername(username);

            if (characters.isEmpty()) {
                model.addAttribute("character", null);
                log.info("No characters found for username: " + username);
            } else {
                CharacterEntity firstCharacter = characters.get(0);  // Fix: get the first character entity
                CharacterDTO characterDTO = new CharacterDTO(firstCharacter);
                model.addAttribute("character", characterDTO);
                log.info("Character found: " + characterDTO);
            }
        } catch (IOException e) {
            model.addAttribute("character", null);
            log.error("Error retrieving character: " + e.getMessage());
        }
        return "my-page/regist";
    }

    @PostMapping("/confirm")
    public String confirmCharacter(@RequestParam String characterName, HttpSession session, Model model) throws IOException {
        UserEntity loggedInUser = (UserEntity) session.getAttribute("user");
        log.info("characterName===>>>{}" ,characterName);
        log.info("getUsername====>>{}",loggedInUser.getUsername());
        log.info("getPassword====>>{}",loggedInUser.getPassword());
        List<CharacterEntity> characters = characterService.findCharacterByUsername(characterName);

        

        return "my-page/regist";
    }
}
