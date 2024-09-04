package com.ysmeta.lostark.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysmeta.lostark.dto.CharacterDTO;
import com.ysmeta.lostark.entity.CharacterEntity;
import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.repository.CharacterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author : ejum
 * @fileName : CharacterService
 * @since : 8/25/24
 */
@Service
@Slf4j
public class CharacterService {

    private final RestTemplate restTemplate;
    private final CharacterRepository characterRepository;
    private final ObjectMapper objectMapper;

    public CharacterService(RestTemplate restTemplate, CharacterRepository characterRepository, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.characterRepository = characterRepository;
        this.objectMapper = objectMapper;
    }

    public CharacterDTO getCharacterByUsername(String username) {
        try {
            List<CharacterEntity> characters = findCharacterByUsername(username);

            if (characters.isEmpty()) {
                return null;
            }

            // 사용자가 입력한 username과 동일한 캐릭터를 찾기
            CharacterEntity matchedCharacter = characters.stream()
                    .filter(character -> character.getCharacterName().equalsIgnoreCase(username))
                    .findFirst()
                    .orElse(null);

            if (matchedCharacter != null) {
                return new CharacterDTO(matchedCharacter);
            }

        } catch (IOException e) {
            log.error("Error retrieving character: {}", e.getMessage());
        }
        return null;
    }

    public CharacterDTO confirmCharacter(String characterName, UserEntity loggedInUser) {
        try {
            List<CharacterEntity> characters = findCharacterByUsername(loggedInUser.getUsername());

            CharacterEntity matchedCharacter = characters.stream()
                    .filter(character -> character.getCharacterName().equalsIgnoreCase(characterName))
                    .findFirst()
                    .orElse(null);

            if (matchedCharacter != null) {
                return new CharacterDTO(matchedCharacter);
            }

        } catch (IOException e) {
            log.error("Error confirming character: {}", e.getMessage());
        }
        return null;
    }

    public List<CharacterEntity> findCharacterByUsername(String username) throws IOException {
        String url = "https://developer-lostark.game.onstove.com/characters/";
        String bearer = "Bearer ";
        String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyIsImtpZCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyJ9.eyJpc3MiOiJodHRwczovL2x1ZHkuZ2FtZS5vbnN0b3ZlLmNvbSIsImF1ZCI6Imh0dHBzOi8vbHVkeS5nYW1lLm9uc3RvdmUuY29tL3Jlc291cmNlcyIsImNsaWVudF9pZCI6IjEwMDAwMDAwMDA1NDI3OTIifQ.aOBjUrGOo41U0JAiJ9xykqi2YzKloZjwpJVsswphrz-BDhicukFTr7lAfpPVrWZFiZkF4AcJ5LBoliHH_5IZFmwMA-E0iYEFbCx1_qGHjyIpXT3giMpY4UN4NlA1s_g3Of6d8verHHh9LVMAkY-h-wKU9bg4E3JieuS5-cDQVAHGKbSUWDun8yV3wU82AWXZEA80iw3X9q_HK3Y-2fArzYyMZ1YQixtJv2qgj1jIENhjtwmQPnUT592c5Jkf_-rZN9g66kViLDrg3IZx7gQnQWzazcSHedlSlJdG4BOtQ-NTHViVSRMaIjivnakA95ni0uvy3pU6eEJQve9BGwd7Zw";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearer + apiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + URLEncoder.encode(username, StandardCharsets.UTF_8) + "/siblings",
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        // JSON 응답을 DTO 목록으로 변환
        String responseBody = responseEntity.getBody();

        List<CharacterDTO> characterDTOList = objectMapper.readValue(
                responseBody,
                objectMapper.getTypeFactory().constructCollectionType(List.class, CharacterDTO.class)
        );

        return convertDTOsToEntities(characterDTOList);
    }

    private List<CharacterEntity> convertDTOsToEntities(List<CharacterDTO> dtos) {
        return dtos.stream().map(dto -> {
            CharacterEntity entity = new CharacterEntity();
            entity.setServerName(dto.getServerName());
            entity.setCharacterName(dto.getCharacterName());
            entity.setCharacterLevel(dto.getCharacterLevel());
            entity.setCharacterClassName(dto.getCharacterClassName());
            entity.setItemAvgLevel(dto.getItemAvgLevel());
            entity.setItemMaxLevel(dto.getItemMaxLevel());
            return entity;
        }).toList();
    }

    public void saveCharacterForUser(CharacterEntity character, UserEntity user) {
        // 캐릭터와 유저 간의 연관 설정
        character.setUser(user);
        character.setConfirmed(true); // 필요시, 확인 여부를 설정
        characterRepository.save(character);
    }

        public List<CharacterEntity> getCharactersByUserId(Long userId) {
            return characterRepository.findByUserId(userId);
        }

    public void deleteCharacter(Long characterId) {
        characterRepository.deleteById(characterId);
    }



    public CharacterEntity findById(Long id) {
        return characterRepository.findById(id).orElse(null);
    }
}