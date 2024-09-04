package com.ysmeta.lostark.repository;

import com.ysmeta.lostark.entity.GuestbookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 방명록 Repository
 *
 * @author : minjooo
 * @fileName : GuestbookRepository
 * @since : 2024/09/03
 */

@Repository
public interface GuestbookRepository extends JpaRepository<GuestbookEntity, Long> {
    List<GuestbookEntity> findByTargetUserId(Long userId);
}