package com.ysmeta.lostark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "recruitment")
public class RecruitmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String goal;

    @Column(nullable = false)
    private String time; // 시간 필드

    @Column(nullable = false)
    private String challengeTime;

    @Column(nullable = false)
    private LocalDate startDate; // 출발 날짜 추가

    @Column(nullable = false)
    private String proficiency;

    @Column(nullable = false)
    private Integer recruitmentCount; // 모집 인원 추가

    @Column(nullable = true)
    private Integer applyCount; // 지원 인원

    @Column(name = "status")
    private String status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "characters", nullable = true)
    private CharacterEntity characterEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "recruitmentEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<RecruitmentTeamEntity> recruitmentTeams;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }
}