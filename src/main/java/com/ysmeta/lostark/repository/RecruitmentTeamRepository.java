package com.ysmeta.lostark.repository;

import com.ysmeta.lostark.entity.CharacterEntity;
import com.ysmeta.lostark.entity.RecruitmentEntity;
import com.ysmeta.lostark.entity.RecruitmentTeamEntity;
import com.ysmeta.lostark.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : minjooo
 * @fileName : RecruitmentTeamRepository
 * @since : 2024/09/19
 */

@Repository
public interface RecruitmentTeamRepository extends JpaRepository<RecruitmentTeamEntity, Long> {
    boolean existsByUserAndRecruitmentEntityAndCharacterEntity(UserEntity user, RecruitmentEntity recruitmentEntity, CharacterEntity characterEntity);
}