package com.ysmeta.lostark.repository;

import com.ysmeta.lostark.entity.RecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : ejum
 * @fileName : RecruitmentRepository
 * @since : 8/25/24
 */
@Repository
public interface RecruitmentRepository extends JpaRepository<RecruitmentEntity, Long> {
    List<RecruitmentEntity> findAllByOrderByCreatedDateDesc();

    List<RecruitmentEntity> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE RecruitmentEntity r SET r.applyCount = r.applyCount - 1 WHERE r.id = :id")
    void decrementApplyCount(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE RecruitmentEntity r SET r.status = '모집 중' WHERE r.id = :id")
    void setRecruitmentStatusToOngoing(@Param("id") Long id);
}