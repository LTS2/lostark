package com.ysmeta.lostark.repository;

import com.ysmeta.lostark.entity.RecruitmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : ejum
 * @fileName : RecruitmentRepository
 * @since : 8/25/24
 */
@Repository
public interface RecruitmentRepository extends JpaRepository<RecruitmentEntity, Long> {
    List<RecruitmentEntity> findAllByOrderByCreatedDateDesc();
}