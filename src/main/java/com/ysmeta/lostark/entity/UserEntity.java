package com.ysmeta.lostark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String picture;
    @OneToMany(mappedBy = "user")
    private List<CharacterEntity> characters;
    @OneToMany(mappedBy = "user")
    private List<GuestbookEntity> writtenGuestbooks; // 작성한 방명록 리스트
    @OneToMany(mappedBy = "targetUser")
    private List<GuestbookEntity> receivedGuestbooks; // 받은 방명록 리스트
}