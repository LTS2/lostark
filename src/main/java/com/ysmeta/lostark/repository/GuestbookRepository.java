package com.ysmeta.lostark.repository;

import com.ysmeta.lostark.entity.GuestbookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestbookRepository extends JpaRepository<GuestbookEntity, Long> {
    List<GuestbookEntity> findByTargetUserIdOrderByCreatedAtDesc(Long targetUserId);
}