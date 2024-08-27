package com.ysmeta.lostark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author : ejum
 * @fileName : RecruitmentEntity
 * @since : 8/25/24
 */
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
    private String day;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private String challengeTime;

    @Column(nullable = false)
    private LocalDate startDate; // 출발 날짜 추가

    @Column(nullable = false)
    private String proficiency;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }
}