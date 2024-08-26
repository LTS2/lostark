package com.ysmeta.lostark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : ejum
 * @fileName : RecruitmentEntity
 * @since : 8/25/24
 */
@Entity
@Getter
@Setter
public class RecruitmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String goal;

    @Column(nullable = false)
    private String day;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private String challengeTime;

    @Column(nullable = false)
    private String proficiency;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


}