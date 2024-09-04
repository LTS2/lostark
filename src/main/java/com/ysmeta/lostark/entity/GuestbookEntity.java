package com.ysmeta.lostark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 방명록 엔티티
 *
 * @author : minjooo
 * @fileName : GuestbookEntity
 * @since : 2024/09/03
 */

@Getter
@Setter
@Entity
@ToString
@Table(name = "guestbook")
public class GuestbookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user; // 방명록 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id", nullable = false)
    private UserEntity targetUser; // 방명록이 작성되는 대상 회원

    @Column(name = "username", nullable = false)
    private String username; // 방명록 작성자 닉네임

    @Column(name = "comment", nullable = false, length = 1000)
    private String comment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}