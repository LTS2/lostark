package com.ysmeta.lostark.service;

import com.ysmeta.lostark.entity.CharacterEntity;
import com.ysmeta.lostark.entity.RecruitmentEntity;
import com.ysmeta.lostark.entity.RecruitmentTeamEntity;
import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.repository.CharacterRepository;
import com.ysmeta.lostark.repository.RecruitmentRepository;
import com.ysmeta.lostark.repository.RecruitmentTeamRepository;
import com.ysmeta.lostark.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


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
    private final UserRepository userRepository;

    @Autowired
    public RecruitmentTeamService(RecruitmentTeamRepository recruitmentTeamRepository,
                                  RecruitmentRepository recruitmentRepository,
                                  CharacterRepository characterRepository,
                                  UserRepository userRepository) {
        this.recruitmentTeamRepository = recruitmentTeamRepository;
        this.recruitmentRepository = recruitmentRepository;
        this.characterRepository = characterRepository;
        this.userRepository = userRepository;
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

    public List<RecruitmentTeamEntity> findAllRecruitmentTeams() {
        return recruitmentTeamRepository.findAll();
    }

    public List<RecruitmentTeamEntity> findTeamsByRecruitmentId(Long recruitmentId) {
        return recruitmentTeamRepository.findByRecruitmentEntity_Id(recruitmentId);
    }

    @Transactional
    public void cancelApplication(Long id, Long userId, HttpSession session) {
        // 세션에서 사용자 정보 가져오기
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null || !user.getId().equals(userId)) {
            throw new IllegalArgumentException("Invalid user session");
        }

        // 모집글 엔티티 찾기
        RecruitmentEntity recruitment = recruitmentRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Recruitment not found"));

        // 지원 취소
        recruitmentTeamRepository.deleteByRecruitmentEntityAndUser(recruitment, user);
        recruitmentRepository.decrementApplyCount(id);
        recruitmentRepository.setRecruitmentStatusToOngoing(id);
    }
}