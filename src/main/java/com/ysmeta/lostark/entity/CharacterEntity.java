package com.ysmeta.lostark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "characters")
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String serverName;
    @Column(nullable = false)
    private String characterName;
    @Column
    private Integer characterLevel;
    @Column(nullable = false)
    private String characterClassName;
    @Column
    private String itemAvgLevel;
    @Column
    private String itemMaxLevel;
    private boolean confirmed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}