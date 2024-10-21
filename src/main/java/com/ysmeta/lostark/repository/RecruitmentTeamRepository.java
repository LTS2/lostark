package com.ysmeta.lostark.repository;

import com.ysmeta.lostark.entity.CharacterEntity;
import com.ysmeta.lostark.entity.RecruitmentEntity;
import com.ysmeta.lostark.entity.RecruitmentTeamEntity;
import com.ysmeta.lostark.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitmentTeamRepository extends JpaRepository<RecruitmentTeamEntity, Long> {
  boolean existsByUserAndRecruitmentEntityAndCharacterEntity(UserEntity user, RecruitmentEntity recruitmentEntity, CharacterEntity characterEntity);
  List<RecruitmentTeamEntity> findByRecruitmentEntity_Id(Long recruitmentId);
  List<RecruitmentTeamEntity> findByUserId(Long id);
  void deleteByRecruitmentEntityAndUser(RecruitmentEntity recruitment, UserEntity user);

}