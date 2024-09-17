package com.ysmeta.lostark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 게시판 엔티티
 *
 * @author : minjooo
 * @fileName : BoardEntity
 * @since : 2024/09/17
 */

@Getter
@Setter
@Entity
@ToString
@Table(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; // 게시글 작성자

    @Column(name = "title", nullable = false)
    private String title; // 제목

    @Column(name = "content", nullable = false)
    private String content; // 내용

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 작성일

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정일

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now(); // 업데이트
    }
}