package com.ysmeta.lostark.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ysmeta.lostark.entity.CharacterEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterDTO {
    @JsonProperty("ServerName")
    private String serverName;

    @JsonProperty("CharacterName")
    private String characterName;

    @JsonProperty("CharacterLevel")
    private Integer characterLevel;

    @JsonProperty("CharacterClassName")
    private String characterClassName;

    @JsonProperty("ItemAvgLevel")
    private String itemAvgLevel;

    @JsonProperty("ItemMaxLevel")
    private String itemMaxLevel;
    // 기본 생성자 추가
    public CharacterDTO() {
    }
    public CharacterDTO(CharacterEntity character) {
        this.serverName = character.getServerName();
        this.characterName = character.getCharacterName();
        this.characterLevel = character.getCharacterLevel();
        this.characterClassName = character.getCharacterClassName();
        this.itemAvgLevel = character.getItemAvgLevel();
        this.itemMaxLevel = character.getItemMaxLevel();
    }
}