package com.ysmeta.lostark.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * @author : ejum
 * @fileName : CharacterEntity
 * @since : 8/22/24
 */
@Entity
public class CharactersEntity {


    @Id
    private Long id;

    private String ServerName;

}
