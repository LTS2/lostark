package com.ysmeta.lostark.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * @author : ejum
 * @fileName : UserEntity
 * @since : 8/22/24
 */
@Entity
public class UserEntity {


    @Id
    private Long id;
    private String username;
    private String password;
//    private CharacterEntity character;

}
