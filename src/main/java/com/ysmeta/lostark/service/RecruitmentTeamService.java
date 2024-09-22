package com.ysmeta.lostark.service;

import com.ysmeta.lostark.entity.CharacterEntity;
import com.ysmeta.lostark.entity.RecruitmentEntity;
import com.ysmeta.lostark.entity.RecruitmentTeamEntity;
import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.repository.CharacterRepository;
import com.ysmeta.lostark.repository.RecruitmentRepository;
import com.ysmeta.lostark.repository.RecruitmentTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : minjooo
 * @fileName : RecruitmentTeamService
 * @since : 2024/09/19
 */

@Service
public class RecruitmentTeamService {

    private final RecruitmentTeamRepository recruitmentTeamRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final CharacterRepository characterRepository;

    @Autowired
    public RecruitmentTeamService(RecruitmentTeamRepository recruitmentTeamRepository,
                                  RecruitmentRepository recruitmentRepository,
                                  CharacterRepository characterRepository) {
        this.recruitmentTeamRepository = recruitmentTeamRepository;
        this.recruitmentRepository = recruitmentRepository;
        this.characterRepository = characterRepository;
    }


    public void saveRecruitmentCharacter(Long recruitmentId, String selectedCharacterId, UserEntity user) {
        RecruitmentEntity recruitmentEntity = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new RuntimeException("모집글 없음 :: " + recruitmentId));
        CharacterEntity characterEntity = characterRepository.findById(Long.parseLong(selectedCharacterId))
                .orElseThrow(() -> new RuntimeException("캐릭터 없음 :: " + selectedCharacterId));

        boolean alreadyApplied = recruitmentTeamRepository.existsByUserAndRecruitmentEntityAndCharacterEntity(user, recruitmentEntity, characterEntity);

        if (alreadyApplied) {
            throw new RuntimeException("이미 지원한 모집글입니다.");
        }

        RecruitmentTeamEntity recruitmentTeamEntity = new RecruitmentTeamEntity();
        recruitmentTeamEntity.setUser(user);
        recruitmentTeamEntity.setRecruitmentEntity(recruitmentEntity);
        recruitmentTeamEntity.setCharacterEntity(characterEntity);

        recruitmentEntity.setApplyCount(recruitmentEntity.getApplyCount() + 1);

        if (recruitmentEntity.getApplyCount() >= recruitmentEntity.getRecruitmentCount()) {
            recruitmentEntity.setStatus("모집 완료");
        }

        recruitmentTeamRepository.save(recruitmentTeamEntity);
    }
}