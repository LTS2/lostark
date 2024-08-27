package com.ysmeta.lostark.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TestService {

    private final RestTemplate restTemplate;

    public TestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String findCharacterName(String username) {
        String url = "https://developer-lostark.game.onstove.com/characters/";
        String bearer = "Bearer ";
        String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyIsImtpZCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyJ9.eyJpc3MiOiJodHRwczovL2x1ZHkuZ2FtZS5vbnN0b3ZlLmNvbSIsImF1ZCI6Imh0dHBzOi8vbHVkeS5nYW1lLm9uc3RvdmUuY29tL3Jlc291cmNlcyIsImNsaWVudF9pZCI6IjEwMDAwMDAwMDA1NDI3OTIifQ.aOBjUrGOo41U0JAiJ9xykqi2YzKloZjwpJVsswphrz-BDhicukFTr7lAfpPVrWZFiZkF4AcJ5LBoliHH_5IZFmwMA-E0iYEFbCx1_qGHjyIpXT3giMpY4UN4NlA1s_g3Of6d8verHHh9LVMAkY-h-wKU9bg4E3JieuS5-cDQVAHGKbSUWDun8yV3wU82AWXZEA80iw3X9q_HK3Y-2fArzYyMZ1YQixtJv2qgj1jIENhjtwmQPnUT592c5Jkf_-rZN9g66kViLDrg3IZx7gQnQWzazcSHedlSlJdG4BOtQ-NTHViVSRMaIjivnakA95ni0uvy3pU6eEJQve9BGwd7Zw"; // 여기에 유효한 API 키를 넣어주세요.

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearer + apiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url + URLEncoder.encode(username, StandardCharsets.UTF_8) + "/siblings",
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        return responseEntity.getBody();
    }

}